package com.example.moviecataloguefordicoding.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.moviecataloguefordicoding.room.ShowDao;
import com.example.moviecataloguefordicoding.room.ShowDatabase;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class MyContentProvider extends ContentProvider {
    private ShowDao showDAO;
    private static final String DB_TABLE = "movie";
    private static final String AUTHORITY = "com.example.moviecataloguefordicoding.provider";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int CODE_FAV_DIR = 1;

    static {
        uriMatcher.addURI(AUTHORITY, DB_TABLE, CODE_FAV_DIR);
    }

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public boolean onCreate() {
        ShowDatabase showDatabase = ShowDatabase.getInstance(getContext());
        showDAO = showDatabase.showDao();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        final int code = uriMatcher.match(uri);
        if (code == CODE_FAV_DIR) {
            final Context context = getContext();
            if (context == null)
                return null;
            final Cursor cursor;
            cursor = showDAO.getAllMovieData();

            Log.d(TAG, "query: "+uri);
            cursor.setNotificationUri(context.getContentResolver(), uri);
            return cursor;
        } else {
            throw new IllegalArgumentException("Unknown Uri: " + uri);
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }
}
