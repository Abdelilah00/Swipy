package com.alexis.swipy.Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.alexis.swipy.Modules.Book;
import java.util.ArrayList;

public class MySqliteDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MySliteDB.db";
    private static final int DATABASE_VERSION = 1;
    private static final String BOOKS_TABLE_NAME = "Books";

    public MySqliteDB(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("CREATE TABLE `books` (" +
                " `BookId` integer primary key," +
                " `Title` varchar(50) DEFAULT NULL," +
                " `Description` mediumtext," +
                " `Image` blob," +
                " `Prix` integer DEFAULT '0')");

        db.execSQL("CREATE TABLE `questions` (" +
                "  `QuestionId` integer primary key," +
                "  `ChapitreId` integer DEFAULT NULL," +
                "  `Question` varchar(500) DEFAULT NULL," +
                "  `Difficulty` integer DEFAULT '1'," +
                "  FOREIGN KEY (`ChapitreId`) REFERENCES `chapitres` (`ChapitreId`))");

        db.execSQL("CREATE TABLE `propositions` ("+
                "  `PropositionId` integer primary key," +
                "  `QuestionId` integer DEFAULT NULL," +
                "  `Response` tinyint(1) DEFAULT '0'," +
                "  FOREIGN KEY (`QuestionId`) REFERENCES `questions` (`QuestionId`))");

        db.execSQL("CREATE TABLE `events` (" +
                " `EventId` integer primary key," +
                " `BookId` integer DEFAULT NULL," +
                " `EventName` mediumtext," +
                " `EventOrder` integer DEFAULT NULL," +
                "  FOREIGN KEY (`BookId`) REFERENCES `books`(`BookId`))");

        db.execSQL("CREATE TABLE `response_to_questions` (" +
                "  `response_to_questions_id` integer primary key autoincrement," +
                "  `QuestionId` integer not null," +
                "  `PropositionId` integer not null)");

        db.execSQL("CREATE TABLE `response_to_event` (" +
                "  `response_to_event_id` integer primary key autoincrement," +
                "  `BookId` integer not null," +
                "  `EventId` integer not null)");

        Log.i("Sqlite", "onCreate : Sqlite Data base Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS books");
        db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS propositions");
        db.execSQL("DROP TABLE IF EXISTS response_to_questions");
        db.execSQL("DROP TABLE IF EXISTS response_to_event");
        onCreate(db);

        Log.i("Sqlite", "onCreate : Sqlite Data base Upgraded");
    }

    public boolean insertBook (Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(BOOKS_TABLE_NAME, null, book.getContentValue());
        return true;
    }

    public boolean updateBook (Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(BOOKS_TABLE_NAME, book.getContentValue(), "BookId = ? ", new String[] { Integer.toString(book.getId()) } );
        return true;
    }
    public Integer deleteBook (Book book) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(BOOKS_TABLE_NAME, "BookId = ? ", new String[] { Integer.toString(book.getId()) });
    }

    public Book getBook(int BookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from "+BOOKS_TABLE_NAME+" where BookId="+BookId+"", null );
        cursor.moveToFirst();
        return new Book(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getBlob(3),cursor.getInt(4));
    }
    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = new ArrayList<>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from books", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            books.add(new Book(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getBlob(3),cursor.getInt(4)));
            cursor.moveToNext();
        }
        return books;
    }



    public int numberOfBooks(){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, BOOKS_TABLE_NAME);
    }
}
