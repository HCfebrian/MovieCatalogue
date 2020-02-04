package com.example.moviecataloguefordicoding.network.Callback;

import com.example.moviecataloguefordicoding.model.ModelTV;

import java.util.List;

public interface OnGetTVCallback {

    void onSuccess(List<ModelTV> TV);

    void onError();
}