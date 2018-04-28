package com.example.dany.moviesapp.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.dany.moviesapp.model.Movie;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Dany on 28.4.2018 г..
 */

@Dao
public interface MovieDao {

    @Query("select * from Movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("select * from Movie where id = :id")
    LiveData<Movie> getMovieById(int id);

    @Query("select * from Movie where rating = :rating")
    LiveData<List<Movie>> getMoviesByRating(int rating);

    @Query("select * from Movie where year = :year")
    LiveData<List<Movie>> getMoviesByYear(int year);

    @Insert(onConflict = REPLACE)
    void insertMovie(Movie movie);

    @Update
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

}