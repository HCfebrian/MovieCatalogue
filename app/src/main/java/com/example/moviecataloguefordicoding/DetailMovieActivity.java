package com.example.moviecataloguefordicoding;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.moviecataloguefordicoding.model.ModelFilm;
import com.example.moviecataloguefordicoding.model.ModelTV;
import com.example.moviecataloguefordicoding.room.ShowDatabase;
import com.example.moviecataloguefordicoding.widget.WidgetMovieFavotite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class DetailMovieActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_FILM = "EXTRA_FILM";
    public static final String BASE_URL= "https://image.tmdb.org/t/p/w500";

    public static final String EXtra_TV = "EXTRA_TV";
    static ModelFilm movie;
    static ModelTV tv;
    int istv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_show);

        TextView tvTitle, tvDescription, tvGenre, tvScore, tvRelease;
        ImageView ivMainPoster, ivBackground;
        movie = getIntent().getParcelableExtra(EXTRA_FILM);
        tv = getIntent().getParcelableExtra(EXtra_TV);

        tvTitle = findViewById(R.id.tv_title);
        tvGenre = findViewById(R.id.tv_genre);
        ivBackground = findViewById(R.id.iv_background);
        ivMainPoster = findViewById(R.id.iv_main_poster);
        tvDescription = findViewById(R.id.tv_description);
        tvScore = findViewById(R.id.tv_score);
        tvRelease = findViewById(R.id.tv_release_year);


        try {
            tvTitle.setText(Objects.requireNonNull(movie).getTitle());
            tvGenre.setText(movie.getGenre());
            tvDescription.setText(movie.getDescription());
            tvScore.setText(movie.getRating());
            tvRelease.setText(movie.getRelease());

            CircularProgressDrawable progressDrawable = new CircularProgressDrawable(this);
            progressDrawable.setStrokeWidth(5f);
            progressDrawable.setCenterRadius(30f);
            progressDrawable.start();

            Glide.with(this)
                    .load(BASE_URL + movie.getBackground())
                    .placeholder(progressDrawable)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .into(ivBackground);

            Glide.with(this)
                    .load(BASE_URL + movie.getPhoto())
                    .transform(new RoundedCornersTransformation(20, 10), new CenterCrop())
                    .into(ivMainPoster);


            istv = 0;
        } catch (Exception e) {
            tvTitle.setText(Objects.requireNonNull(tv).getTitle());
            tvGenre.setText(tv.getGenre());
            tvDescription.setText(tv.getDescription());
            tvScore.setText(tv.getRating());
            tvRelease.setText(tv.getRelease());

            CircularProgressDrawable progressDrawable = new CircularProgressDrawable(this);
            progressDrawable.setStrokeWidth(5f);
            progressDrawable.setCenterRadius(30f);
            progressDrawable.start();

            Glide.with(this)
                    .load(BASE_URL + tv.getBackground())
                    .placeholder(progressDrawable)
                    .into(ivBackground);

            Glide.with(this)
                    .load(BASE_URL + tv.getPhoto())
                    .transform(new RoundedCornersTransformation(20, 10), new CenterCrop())
                    .into(ivMainPoster);
            istv = 1;
        }


        FloatingActionButton favorite = findViewById(R.id.fab);
        favorite.setOnClickListener(this);


    }

    public void back(View view) {
        finish();
    }


    @Override
    public void onClick(View view) {
        if (istv == 0) {
            final favThread task = new favThread(getApplicationContext());
            task.execute();
        } else {
            final favTVThread task = new favTVThread(getApplicationContext());
            task.execute();
        }
        
    }

    static private class favThread extends AsyncTask<Void, Void, Void> {
        static Context mContext;
        int isFav;


        favThread(final Context context) {
            mContext = context;

        }


        @Override
        protected Void doInBackground(Void... voids) {

            ShowDatabase showDatabase = ShowDatabase.getInstance(mContext);
            isFav =showDatabase.showDao().isFavMovie(movie.getId());
            if(isFav ==1){
                showDatabase.showDao().deleteMovie(movie);
            }else{
            ModelFilm show = new ModelFilm();
            show.setId(movie.getId());
            show.setBackground(movie.getBackground());
            show.setDescription(movie.getDescription());
            show.setPhoto(movie.getPhoto());
            show.setPopularity(movie.getPopularity());
            show.setRating(movie.getRating());
            show.setRelease(movie.getRelease());
            show.setTitle(movie.getTitle());
            show.setGenre(movie.getGenre());
            show.setTv(movie.getTv());
            show.setFav(1);
            Log.d("DB", movie.getTitle());
            showDatabase.showDao().insertShow(show);

            }
            Log.d("DB", "AsyncTask finished");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(isFav==1){
                Toast.makeText(mContext,"DELETED FROM FAVORITE",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(mContext,"ADDED TO FAVORITE",Toast.LENGTH_SHORT).show();
            }


        }
    }

    static private class favTVThread extends AsyncTask<Void, Void, Void> {
        static Context mContext;
        int isFav;


        favTVThread(final Context context) {
            mContext = context;

        }

        @Override
        protected Void doInBackground(Void... voids) {

            ShowDatabase showDatabase = ShowDatabase.getInstance(mContext);
            isFav = showDatabase.showDao().isFavTV(tv.getId());
            if(isFav==1){
                showDatabase.showDao().deleteTV(tv);
            }else{
            ModelTV show = new ModelTV();
            show.setId(tv.getId());
            show.setBackground(tv.getBackground());
            show.setDescription(tv.getDescription());
            show.setPhoto(tv.getPhoto());
            show.setPopularity(tv.getPopularity());
            show.setRating(tv.getRating());
            show.setRelease(tv.getRelease());
            show.setTitle(tv.getTitle());
            show.setGenre(tv.getGenre());
            show.setTv(tv.getTv());
            show.setFav(1);
            showDatabase.showDao().insertShowTV(show);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);


            if(isFav==1){
                Toast.makeText(mContext,"DELETED FROM FAVORITE",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(mContext,"ADDED TO FAVORITE",Toast.LENGTH_SHORT).show();
            }



        }
    }


}


