package com.example.moviecataloguefordicoding.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.moviecataloguefordicoding.R;
import com.example.moviecataloguefordicoding.model.ModelFilm;
import com.example.moviecataloguefordicoding.room.ShowDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<ModelFilm> mWidgetItems = new ArrayList<>();
    private final Context mContext;
    private static final String BASE_URL= "https://image.tmdb.org/t/p/w500";


    StackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ShowDatabase showDatabase = ShowDatabase.getInstance(mContext);
        List<ModelFilm> shows = showDatabase.showDao().getFavMovie();
        mWidgetItems.addAll(shows);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);


        Bitmap bmp = null;
        try {
            bmp =Glide.with(mContext)
                    .asBitmap()
                    .load(BASE_URL + mWidgetItems.get(i).getPhoto() )
                    .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Widget Load Error", "error");
        }

        rv.setImageViewBitmap(R.id.imageView, bmp);
        Bundle extras = new Bundle();
        extras.putInt(WidgetMovieFavotite.EXTRA_ITEM, i);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
