package com.example.moviecataloguefordicoding.network;

import com.example.moviecataloguefordicoding.network.response.MoviesResponse;
import com.example.moviecataloguefordicoding.network.response.TVResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIMovie {
    @GET("discover/movie")
    Call<MoviesResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
    @GET("discover/tv")
    Call<TVResponse> getPopularTV(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("search/movie")
    Call<MoviesResponse> searchMovie(
            @Query("api_key") String apiKey,
            @Query("language") String language
            ,@Query("query") String query
    );
    @GET("search/tv")
    Call<TVResponse> searchTV(
            @Query("api_key") String apiKey,
            @Query("language") String language
            ,@Query("query") String query
    );
    @GET("discover/movie")
    Call<MoviesResponse> getTodayRelease(
            @Query("api_key") String apiKey,
            @Query("primary_release_date.gte") String todayDateGTE,
            @Query("primary_release_date.lte") String todayDateLTE

    );
}
