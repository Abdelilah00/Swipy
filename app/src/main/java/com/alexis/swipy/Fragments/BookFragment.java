package com.alexis.swipy.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alexis.swipy.Modules.Book;
import com.alexis.swipy.Classes.MySharedPreferences;
import com.alexis.swipy.Classes.MySqliteDB;
import com.alexis.swipy.R;
import com.alexis.swipy.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFragment extends Fragment {
    private Book book;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        final ImageView iv_book = view.findViewById(R.id.iv_book);
        final TextView tv_title = view.findViewById(R.id.tv_title);
        final TextView tv_description = view.findViewById(R.id.tv_description);
        final TextView tv_prix = view.findViewById(R.id.tv_prix);
        final RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        Button btn_add_book = view.findViewById(R.id.btn_add_book);

        Call<Book> call = RetrofitClient
                .getInstance()
                .getApi()
                .getBook(getArguments().getString("BookId"));

        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                book = response.body();

                tv_title.setText(book.getTitle());
                tv_description.setText(book.getDescription());
                tv_prix.setText(String.valueOf(book.getPrix()));
                ratingBar.setRating(book.getRating());
                Picasso.get()
                        .load(RetrofitClient.BASE_URL+"uploads/" +book.getImageUrl())
                        .placeholder(R.drawable.ic_book)
                        .error(R.drawable.ic_error)
                        .into(iv_book);

            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(getActivity(), "Application error", Toast.LENGTH_LONG).show();
            }
        });

        btn_add_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySharedPreferences mySharedPreferences = new MySharedPreferences(getActivity(),"cartBooks");

                try {

                    if (book.getPrix() == 0){
                        MySqliteDB mySqliteDB = new MySqliteDB(getContext());

                        if (mySqliteDB.insertBook(book)) {
                            Toast.makeText(getContext(), "Book Added to your backpack", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(getContext(), "Free Book Not Added", Toast.LENGTH_LONG).show();
                        }

                    }else{
                        ArrayList<String> arrayList = new ArrayList<>(mySharedPreferences.getArrayList("booksId"));
                        arrayList.add(getArguments().getString("BookId"));
                        mySharedPreferences.saveArrayList("booksId", arrayList);
                        Toast.makeText(getContext(), "Book Added to your carts", Toast.LENGTH_LONG).show();
                    }

                }catch (NullPointerException e) {
                    Toast.makeText(getContext(), "Book Not Added", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        return view;
    }


}
