package com.example.moviecataloguefordicoding.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moviecataloguefordicoding.model.ModelFilm;
import com.example.moviecataloguefordicoding.model.ModelTV;


@Database(entities = {ModelFilm.class, ModelTV.class}, exportSchema = false, version = 17)
public abstract class ShowDatabase extends RoomDatabase {
    private static final String DB_NAME = "show_db";
    private static ShowDatabase instance;

    public static synchronized ShowDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ShowDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract ShowDao showDao();
}
