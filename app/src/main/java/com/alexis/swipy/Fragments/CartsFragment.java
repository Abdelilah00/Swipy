package com.alexis.swipy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.swipy.Modules.Book;
import com.alexis.swipy.Classes.MySharedPreferences;
import com.alexis.swipy.R;
import com.alexis.swipy.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartsFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_carts, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycle_view);


    try{
        MySharedPreferences mySharedPreferences = new MySharedPreferences(getActivity(), "cartBooks");
        ArrayList<String> arrayList =  new ArrayList<>(mySharedPreferences.getArrayList("booksId"));

        if(! arrayList.isEmpty()){
            Call<List<Book>> call = RetrofitClient
                    .getInstance()
                    .getApi()
                    .getBooks(arrayList);

            Log.i("retrofit", "onCreateView CartsFragment: " + arrayList);

            call.enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                    List<Book> books = response.body();


                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), books);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getInteger(R.integer.number_of_grid_books_items)));
                    recyclerView.setAdapter(recyclerViewAdapter);

                }

                @Override
                public void onFailure(Call<List<Book>> call, Throwable t) {
                    Toast.makeText(getActivity(), "Application error", Toast.LENGTH_LONG).show();
                    Log.i("retrofit", "onFailure CartsFragment: " + t);
                }
            });
        }else{
            Toast.makeText(getActivity(), "Your Cart Is empty", Toast.LENGTH_LONG).show();
        }


    }catch(NullPointerException e){
        Toast.makeText(getActivity(), "Your Cart Is empty", Toast.LENGTH_LONG).show();
    } catch (Exception e) {
        e.printStackTrace();
    }

        return view;
    }

    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
        Context context;
        List<Book> books;

        RecyclerViewAdapter(Context context, List<Book> books) {
            this.context = context;
            this.books = books;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);


            int height = parent.getMeasuredHeight() / 3;
            view.setMinimumWidth(height);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, final int position) {

            Picasso.get()
                    .load(RetrofitClient.BASE_URL+"uploads/" +books.get(position).getImageUrl())
                    .placeholder(R.drawable.ic_book)
                    .error(R.drawable.ic_error)
                    .into(holder.iv_book);

            holder.tv_title.setText(books.get(position).getTitle());
            holder.tv_prix.setText("for " + books.get(position).getPrix()+" DH");
            holder.ratingBar.setRating(books.get(position).getRating());

            holder.cardView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                    View mView = getLayoutInflater().inflate(R.layout.alert_dialog_carts, null);

                    Button btnRemove = mView.findViewById(R.id.btn_remove);
                    Button btnDetail = mView.findViewById(R.id.btn_details);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    btnRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "button remove pdf clicked",Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }
                    });
                    btnDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AppCompatActivity activity = (AppCompatActivity) v.getContext();
                            Fragment myFragment = new BookFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("BookId", String.valueOf(books.get(position).getId()));
                            myFragment.setArguments(bundle);
                            activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
                            dialog.hide();
                        }
                    });



                }
            });
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_title;
            private TextView tv_prix;
            private ImageView iv_book;
            private CardView cardView;
            private RatingBar ratingBar;

            MyViewHolder(View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_title);
                tv_prix = itemView.findViewById(R.id.tv_prix);
                iv_book = itemView.findViewById(R.id.imageView);
                ratingBar = itemView.findViewById(R.id.ratingBar);
                cardView = itemView.findViewById(R.id.cardview_book);
            }
        }
    }

}