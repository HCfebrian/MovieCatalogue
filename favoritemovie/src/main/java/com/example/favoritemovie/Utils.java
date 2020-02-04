package com.example.favoritemovie;

import android.net.Uri;

class Utils {
    private static final String TABLE_NAME = "movie";
    private static final String AUTHORITY = "com.example.moviecataloguefordicoding.provider";
    static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    static final String COLUMN_TITLE = "title";
    static final String COLUMN_POPULARITY = "popularity";
    static final String COLUMN_RATING = "rating";
    static final String COLUMN_POSTER = "photo";
    static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500/";
}
