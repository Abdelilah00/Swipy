package com.alexis.swipy;

import com.alexis.swipy.Modules.Book;
import com.alexis.swipy.Modules.Categorie;
import com.alexis.swipy.Modules.DefaultResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("Books/SelectBooks.php")
    Call<List<Book>> getAllBooks();

    @GET("Books/SelectBooks.php")
    Call<List<Book>> getHavingBooks(@Query("search") String search);

    @GET("Categories/SelectCategories.php")
    Call<List<Categorie>> getAllCategories();

    @GET("Books/SelectBookById.php")
    Call<Book> getBook(@Query("BookId") String booksId);

    @GET("Books/SelectBooksById.php")
    Call<List<Book>> getBooks(@Query("BooksId[]") List<String> booksId);

}
