package com.example.moviecataloguefordicoding.network;

import android.util.Log;

import com.example.moviecataloguefordicoding.network.Callback.OnGetMoviesCallback;
import com.example.moviecataloguefordicoding.network.Callback.OnGetTVCallback;
import com.example.moviecataloguefordicoding.network.response.MoviesResponse;
import com.example.moviecataloguefordicoding.network.response.TVResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesRepository {
    private static final String BASE_URL = "https://api.themoviedb.org/3/discover/";
    private static final String LANGUAGE = "en-US";
    private static final String TAG = "Loading Error";

    private static MoviesRepository repository;
    private APIMovie api;

    private MoviesRepository(APIMovie api) {
        this.api = api;
    }

    public static MoviesRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new MoviesRepository(retrofit.create(APIMovie.class));
        }
        return repository;
    }

    public void getMovies(final OnGetMoviesCallback callback) {
        api.getPopularMovies("4317b8fa5af5220fa0aa1b42e4d755ca", LANGUAGE)
                .enqueue(new Callback<MoviesResponse>() {


                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getResults() != null) {
                                callback.onSuccess(moviesResponse.getResults());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                            Log.d(TAG, "onFailure: ");
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    public void getTVs(final OnGetTVCallback callback) {
        api.getPopularTV("4317b8fa5af5220fa0aa1b42e4d755ca", LANGUAGE).enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                if (response.isSuccessful()) {
                    TVResponse moviesResponse = response.body();
                    if (moviesResponse != null && moviesResponse.getResults() != null) {
                        callback.onSuccess(moviesResponse.getResults());
                    } else {
                        callback.onError();
                    }
                } else {
                    callback.onError();
                }

            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
