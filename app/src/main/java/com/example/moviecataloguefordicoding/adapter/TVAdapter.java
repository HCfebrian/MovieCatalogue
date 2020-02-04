package com.example.moviecataloguefordicoding.adapter;


import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.moviecataloguefordicoding.DetailMovieActivity;
import com.example.moviecataloguefordicoding.R;
import com.example.moviecataloguefordicoding.model.ModelTV;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class TVAdapter extends RecyclerView.Adapter<TVAdapter.MovieViewHolder> implements Filterable {

    private final List<ModelTV> listMovie;
    private  List<ModelTV> listMovieAll;
    private static final String BASE_URL= "https://image.tmdb.org/t/p/w500";



    public TVAdapter(ArrayList<ModelTV> list) {
        this.listMovie = list;
    }

    @NonNull
    @Override
    public TVAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_film,viewGroup,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TVAdapter.MovieViewHolder movieViewHolder, int i) {
        final ModelTV film = listMovie.get(i);
        movieViewHolder.tvTitle.setText(film.getTitle());
        movieViewHolder.tvDurasi.setText(film.getPopularity());
        movieViewHolder.tvRating.setText(film.getRating());


        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(movieViewHolder.itemView.getContext());
        progressDrawable.setStrokeWidth(5f);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.start();

        Log.d("tv", "onBindViewHolder: " );
        Glide.with(movieViewHolder.itemView.getContext())
                .load(BASE_URL+film.getPhoto())
                .placeholder(progressDrawable)
                .error(R.drawable.ic_broken_image_black_24dp)
                .transform(new RoundedCornersTransformation(20, 10), new CenterCrop())
                .into(movieViewHolder.ivPoster);

        movieViewHolder.rlItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailMovieActivity.class);
                intent.putExtra("EXTRA_TV",listMovie.get(movieViewHolder.getAdapterPosition()));
                v.getContext().startActivity(intent);
            }
        });
        }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    @Override
    public Filter getFilter() {
        return afterFilter;
    }

    private Filter afterFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<ModelTV> filmList = new ArrayList<>();
            if(charSequence == null|| charSequence.length()==0){
                filmList.addAll(listMovieAll);
            }
            else {
                String fp = charSequence.toString().toLowerCase().trim();

                for(ModelTV film : listMovieAll){
                    if(film.getTitle().toLowerCase().contains(fp)){
                        filmList.add(film);
                    }
                }
            }
            FilterResults fr = new FilterResults();
            fr.values = filmList;
            return fr;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            listMovie.clear();
            listMovie.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };


    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvDurasi,tvRating;
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

    public void setData(ArrayList<ModelTV> items){
        listMovie.clear();
        listMovie.addAll(items);
        listMovieAll = new ArrayList<>(items);
        notifyDataSetChanged();
        Log.d("DATA", "setData: change ");
    }

}


