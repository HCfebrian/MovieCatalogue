package com.example.moviecataloguefordicoding.room;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.moviecataloguefordicoding.model.ModelFilm;
import com.example.moviecataloguefordicoding.model.ModelTV;

import java.util.List;

@Dao
public interface ShowDao {

    @Query("Select * from movie ")
    List<ModelFilm> getFavMovie();
    @Query("Select * from tv ")
    List<ModelTV> getFavTV();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShow(ModelFilm movie);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertShowTV(ModelTV tv);
    @Delete
    void deleteMovie(ModelFilm movie);
    @Delete
    void deleteTV(ModelTV tv);
    @Query("SELECT * FROM movie")
    Cursor getAllMovieData();

    @Query("Select fav from movie where id =:argm")
    int isFavMovie(int argm);
    @Query("Select fav from tv where id =:argm")
    int isFavTV(int argm);


    @Query("SELECT * FROM movie WHERE title LIKE '%' || :arg0 || '%'")
    List<ModelFilm> searchMovieDatabase(String arg0);

    @Query("SELECT * FROM tv WHERE title LIKE '%' || :arg0 || '%'")
    List<ModelTV> searchTvDatabase(String arg0);


}
