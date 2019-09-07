package com.alexis.swipy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.swipy.Modules.Book;
import com.alexis.swipy.Classes.MySqliteDB;
import com.alexis.swipy.R;
import com.alexis.swipy.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BackpackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycle_view);
        MySqliteDB mySqliteDB = new MySqliteDB(getContext());

        List<Book> books = mySqliteDB.getAllBooks();

        if (! books.isEmpty()) {
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), books);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getInteger(R.integer.number_of_grid_books_items)));
            recyclerView.setAdapter(recyclerViewAdapter);

        } else {
            Toast.makeText(getContext(), "Your backpack is empty", Toast.LENGTH_LONG).show();
        }


        return view;
    }


    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{
        Context context;
        List<Book> books;

        RecyclerViewAdapter(Context context, List<Book> books) {
            this.context = context;
            this.books = books;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_book_backpack, parent, false);

            int height = parent.getMeasuredHeight() / 3;
            view.setMinimumWidth(height);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, final int position) {

            Picasso.get()
                    .load(RetrofitClient.BASE_URL + "uploads/" + books.get(position).getImageUrl())
                    .placeholder(R.drawable.ic_book)
                    .error(R.drawable.ic_error)
                    .into(holder.iv_book);

            holder.tv_title.setText(books.get(position).getTitle());
            holder.tv_prix.setText("for " + books.get(position).getPrix() + " DH");

            holder.cardView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                    View mView = getLayoutInflater().inflate(R.layout.alert_dialog_backpack, null);

                    Button btnReadPdf = mView.findViewById(R.id.btn_read_pdf);
                    Button btnQcm = mView.findViewById(R.id.btn_qcm);
                    Button btnEvent = mView.findViewById(R.id.btn_event);
                    Button btnDetail = mView.findViewById(R.id.btn_details);

                    mBuilder.setView(mView);
                    final AlertDialog dialog = mBuilder.create();
                    dialog.show();

                    btnReadPdf.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "button read pdf clicked",Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }
                    });
                    btnQcm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "button Qcm clicked",Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }
                    });
                    btnEvent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "button event clicked",Toast.LENGTH_LONG).show();
                            dialog.hide();
                        }
                    });
                    btnDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "button details clicked",Toast.LENGTH_LONG).show();
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

            MyViewHolder(View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_title);
                tv_prix = itemView.findViewById(R.id.tv_prix);
                iv_book = itemView.findViewById(R.id.imageView);
                cardView = itemView.findViewById(R.id.cardview_book);
            }
        }
    }

}
