package com.example.moviecataloguefordicoding.adapter.Callback;

import java.util.List;

public interface OnGetShowCallback<T> {

    void onSuccess(List<T> show);

    void onError();
}

