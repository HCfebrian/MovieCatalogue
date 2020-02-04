package com.example.moviecataloguefordicoding;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecataloguefordicoding.model.ModelFilm;
import com.example.moviecataloguefordicoding.model.ModelTV;
import com.example.moviecataloguefordicoding.network.Callback.OnGetMoviesCallback;
import com.example.moviecataloguefordicoding.network.Callback.OnGetTVCallback;
import com.example.moviecataloguefordicoding.network.MoviesRepository;
import com.example.moviecataloguefordicoding.room.ShowDatabase;

import java.util.ArrayList;
import java.util.List;

class MovieViewModel extends ViewModel {

    private final ArrayList<ModelFilm> listShow = new ArrayList<>();
    private final ArrayList<ModelTV> tVlistShow = new ArrayList<>();
    private MutableLiveData<ArrayList<ModelFilm>> listShows = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ModelTV>> tVlistShows = new MutableLiveData<>();
    private MoviesRepository moviesRepository = MoviesRepository.getInstance();


    void setMovie(final String showType) {

        if (showType.equals("movie")) {
            moviesRepository.getMovies(new OnGetMoviesCallback() {
                @Override
                public void onSuccess(List<ModelFilm> movies) {
                    listShow.addAll(movies);
                    listShows.postValue(listShow);
                }

                @Override
                public void onError() {

                }
            });

        } else {
            moviesRepository.getTVs(new OnGetTVCallback() {
                @Override
                public void onSuccess(List<ModelTV> TV) {
                    tVlistShow.addAll(TV);
                    tVlistShows.postValue(tVlistShow);
                }

                @Override
                public void onError() {

                }
            });

        }

    }

    void setFavorite(Context context) {
        final showFavThread task = new showFavThread(context);
        task.execute();
    }

    void setFavoriteTV(Context context) {
        final showFavTVThread task = new showFavTVThread(context);
        task.execute();
    }


    LiveData<ArrayList<ModelFilm>> getShow() {
        return listShows;
    }

    LiveData<ArrayList<ModelTV>> getTVShow() {
        return tVlistShows;
    }

    private class showFavThread extends AsyncTask<Void, Void, Void> {

        Context mContext;


        showFavThread(final Context context) {
            mContext = context;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ShowDatabase showDatabase = ShowDatabase.getInstance(mContext);
            List<ModelFilm> shows = showDatabase.showDao().getFavMovie();
            listShow.addAll(shows);
            listShows.postValue(listShow);
            Log.d("DB", "AsyncTask Movie");
            return null;
        }
    }

    private class showFavTVThread extends AsyncTask<Void, Void, Void> {

        Context mContext;


        showFavTVThread(final Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            ShowDatabase showDatabase = ShowDatabase.getInstance(mContext);
            List<ModelTV> shows = showDatabase.showDao().getFavTV();
            tVlistShow.addAll(shows);
            tVlistShows.postValue(tVlistShow);
            Log.d("DB", "AsyncTask TV");
            return null;
        }
    }
}
