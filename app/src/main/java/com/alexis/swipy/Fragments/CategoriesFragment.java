package com.alexis.swipy.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.swipy.Modules.Categorie;
import com.alexis.swipy.R;
import com.alexis.swipy.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categories, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.recycle_view);

        Call<List<Categorie>> call = RetrofitClient
                .getInstance()
                .getApi()
                .getAllCategories();

        call.enqueue(new Callback<List<Categorie>>() {
            @Override
            public void onResponse(Call<List<Categorie>> call, Response<List<Categorie>> response) {
                List<Categorie> categories = response.body();

                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), categories);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), getActivity().getResources().getInteger(R.integer.number_of_grid_cat_items)));
                recyclerView.setAdapter(recyclerViewAdapter);

            }

            @Override
            public void onFailure(Call<List<Categorie>> call, Throwable t) {
                Toast.makeText(getActivity(), "Application error", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
        Context context;
        List<Categorie> categories;

        RecyclerViewAdapter(Context context, List<Categorie> categories) {
            this.context = context;
            this.categories = categories;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(context).inflate(R.layout.item_categorie, parent, false);

            int height = parent.getMeasuredHeight() / 3;
            view.setMinimumWidth(height);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, final int position) {
            Picasso.get()
                    .load(RetrofitClient.BASE_URL+"uploads/" +categories.get(position).getImage())
                    .placeholder(R.drawable.ic_book)
                    .error(R.drawable.ic_error)
                    .into(holder.iv_categorie);

            holder.tv_title.setText(categories.get(position).getName());
            holder.cardView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment myFragment = new BooksFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("CategorieId", String.valueOf(categories.get(position).getId()));
                    myFragment.setArguments(bundle);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();

                }
            });
        }

        @Override
        public int getItemCount() {
            return categories.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_title;
            private ImageView iv_categorie;
            private CardView cardView;

            MyViewHolder(View itemView) {
                super(itemView);
                tv_title = itemView.findViewById(R.id.tv_title);
                iv_categorie = itemView.findViewById(R.id.imageView);
                cardView = itemView.findViewById(R.id.cardview_book);
            }
        }
    }

}


