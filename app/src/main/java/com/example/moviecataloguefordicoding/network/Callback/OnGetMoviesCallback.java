package com.example.moviecataloguefordicoding.network.Callback;

import com.example.moviecataloguefordicoding.model.ModelFilm;

import java.util.List;

public interface OnGetMoviesCallback {

    void onSuccess(List<ModelFilm> movies);

    void onError();
}