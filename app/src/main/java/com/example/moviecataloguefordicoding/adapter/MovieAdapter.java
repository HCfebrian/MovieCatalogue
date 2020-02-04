package com.example.moviecataloguefordicoding.adapter;


import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.moviecataloguefordicoding.DetailMovieActivity;
import com.example.moviecataloguefordicoding.R;
import com.example.moviecataloguefordicoding.model.ModelFilm;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private final List<ModelFilm> listMovie;


    public MovieAdapter(ArrayList<ModelFilm> list) {
        this.listMovie = list;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_film, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieAdapter.MovieViewHolder movieViewHolder, int i) {
        final ModelFilm film = listMovie.get(i);
        movieViewHolder.tvTitle.setText(film.getTitle());
        movieViewHolder.tvDurasi.setText(film.getPopularity());
        movieViewHolder.tvRating.setText(film.getRating());

        Glide.with(movieViewHolder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w500" + film.getPhoto())
                .transform(new RoundedCornersTransformation(20, 10), new CenterCrop())
                .into(movieViewHolder.ivPoster);

        movieViewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
                intent.putExtra("EXTRA_FILM", listMovie.get(movieViewHolder.getAdapterPosition()));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public void setData(ArrayList<ModelFilm> items) {
        listMovie.clear();
        listMovie.addAll(items);
        notifyDataSetChanged();
        Log.d("DATA", "setData: change ");
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvDurasi, tvRating;
        ConstraintLayout rlItem;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_film_photo);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRating = itemView.findViewById(R.id.tv_rv_rating);
            tvDurasi = itemView.findViewById(R.id.tv_rv_durasi);
            rlItem = itemView.findViewById(R.id.list_item);
        }
    }

}


