package com.example.moviecataloguefordicoding;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecataloguefordicoding.adapter.Callback.OnGetShowCallback;
import com.example.moviecataloguefordicoding.model.ModelFilm;
import com.example.moviecataloguefordicoding.model.ModelTV;
import com.example.moviecataloguefordicoding.network.MoviesRepository;
import com.example.moviecataloguefordicoding.room.ShowDatabase;

import java.util.ArrayList;
import java.util.List;

class MovieViewModel extends ViewModel {

    private final ArrayList<ModelFilm> listShow = new ArrayList<>();
    private MutableLiveData<ArrayList<ModelFilm>> listShows = new MutableLiveData<>();
    private final ArrayList<ModelTV> tVlistShow = new ArrayList<>();
    private MutableLiveData<ArrayList<ModelTV>> tVlistShows = new MutableLiveData<>();
    private MoviesRepository moviesRepository = MoviesRepository.getInstance();

    void setFavorite(Context context) {
        final showFavThread task = new showFavThread(context);
        task.execute();
    }
    void setFavoriteTV(Context context) {
        final showFavTVThread task = new showFavTVThread(context);
        task.execute();
    }
    void setMovie(final String showType, @Nullable String query) {

        switch (showType) {
            case "movie":
                moviesRepository.getMovies(new OnGetShowCallback<ModelFilm>() {
                    @Override
                    public void onSuccess(List<ModelFilm> show) {
                        listShow.addAll(show);
                        listShows.postValue(listShow);
                    }

                    @Override
                    public void onError() {

                    }
                });
                break;

            case "search-movie":
                Log.d("search-movie", "setMovie: se");
                moviesRepository.getSearchMovie(query, new OnGetShowCallback<ModelFilm>() {
                    @Override
                    public void onSuccess(List<ModelFilm> show) {
                        listShow.clear();
                        listShow.addAll(show);
                        listShows.postValue(listShow);

                    }

                    @Override
                    public void onError() {
                        Log.e("search-movie", "error");
                    }
                });
                break;

            case "search-tv":

                moviesRepository.getSearchTV(query, new OnGetShowCallback<ModelTV>() {

                    @Override
                    public void onSuccess(List<ModelTV> show) {
                        tVlistShow.clear();
                        tVlistShow.addAll(show);
                        tVlistShows.postValue(tVlistShow);

                    }

                    @Override
                    public void onError() {
                        Log.e("search-movie", "error");
                    }
                });
                break;

            default:
                moviesRepository.getTVs(new OnGetShowCallback<ModelTV>() {
                    @Override
                    public void onSuccess(List<ModelTV> show) {
                        tVlistShow.addAll(show);
                        tVlistShows.postValue(tVlistShow);
                    }

                    @Override
                    public void onError() {

                    }
                });

                break;
        }

    }

    void searchMovie(Context context,String q){
        final searchMovie task = new searchMovie(context,q);
        task.execute();
    }
    void searchTV(Context context,String q){
        final searchTV task = new searchTV(context,q);
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


            return null;
        }
    }

    private class searchMovie extends AsyncTask<Void, Void, Void> {

        Context mContext;
        String q;


        searchMovie(final Context context,String q) {
            mContext = context;
            this.q = q;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ShowDatabase showDatabase = ShowDatabase.getInstance(mContext);
            List<ModelFilm> shows = showDatabase.showDao().searchMovieDatabase(q);
            listShow.clear();
            listShow.addAll(shows);
            listShows.postValue(listShow);
            return null;
        }
    }

    private class searchTV extends AsyncTask<Void, Void, Void> {

        Context mContext;
        String q;


        searchTV(final Context context,String q) {
            mContext = context;
            this.q = q;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            ShowDatabase showDatabase = ShowDatabase.getInstance(mContext);
            List<ModelTV> shows = showDatabase.showDao().searchTvDatabase(q);
            tVlistShow.clear();
            tVlistShow.addAll(shows);
            tVlistShows.postValue(tVlistShow);
            Log.d("DB", "AsyncTask TV");
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
