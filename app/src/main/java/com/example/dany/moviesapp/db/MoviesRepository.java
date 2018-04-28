package com.example.dany.moviesapp.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.dany.moviesapp.model.Movie;

import java.util.List;

/**
 * Created by Dany on 28.4.2018 г..
 */


public class MoviesRepository {

    private MovieDao movieDao;
    private LiveData<List<Movie>> listMovies;
    private LiveData<Movie> movie;

    public MoviesRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        movieDao = appDatabase.movieDao();
        listMovies = movieDao.getAllMovies();
    }

    public LiveData<List<Movie>> getListMovies() {
        return this.listMovies;
    }

    public LiveData<Movie> getMovieById(int id) {
        movie = movieDao.getMovieById(id);
        return movie;
    }

    public LiveData<List<Movie>> getMoviesByRating(int rating) {
        return movieDao.getMoviesByRating(rating);
    }

    public LiveData<List<Movie>> getMoviesByYear(int year) {
        return movieDao.getMoviesByYear(year);
    }

    public void insertMovie(final Movie movie) {
        new insertAsyncTask(movieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao asyncTaskDao;

        insertAsyncTask(MovieDao movieDao) {
            asyncTaskDao = movieDao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            asyncTaskDao.insertMovie(params[0]);
            return null;
        }
    }

    public void updateMovie (Movie movie) {
        new updateAsyncTask(movieDao).execute(movie);
    }

    private static class updateAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        updateAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.updateMovie(params[0]);
            return null;
        }
    }

    public void deleteMovie(Movie movie) {
        new deleteAsyncTask(movieDao).execute(movie);
    }

    private static class deleteAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        deleteAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.deleteMovie(params[0]);
            return null;
        }
    }
}
