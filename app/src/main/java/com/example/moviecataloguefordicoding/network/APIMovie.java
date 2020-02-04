package com.example.moviecataloguefordicoding.network;

import com.example.moviecataloguefordicoding.network.response.MoviesResponse;
import com.example.moviecataloguefordicoding.network.response.TVResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIMovie {
    @GET("movie")
    Call<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("tv")
    Call<TVResponse> getPopularTV(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
