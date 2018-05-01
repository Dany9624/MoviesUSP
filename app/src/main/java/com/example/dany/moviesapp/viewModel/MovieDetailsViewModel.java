package com.example.dany.moviesapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.dany.moviesapp.db.MoviesRepository;
import com.example.dany.moviesapp.model.Movie;

/**
 * Created by Dany on 2.5.2018 г..
 */

public class MovieDetailsViewModel extends AndroidViewModel{

    private MoviesRepository moviesRepository;
    private boolean edit;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        edit = false;
    }

    public LiveData<Movie> getMovieById(int id) {
        return moviesRepository.getMovieById(id);
    }

    public void setEdit(boolean value) {
        this.edit = value;
    }
    public boolean getEdit() {
        return this.edit;
    }

    public void updateMovie(Movie movie) {
        moviesRepository.updateMovie(movie);
    }

    public void deleteMovie(Movie movie) {
        moviesRepository.deleteMovie(movie);
    }

}
