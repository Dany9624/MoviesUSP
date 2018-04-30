package com.example.dany.moviesapp.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.dany.moviesapp.db.MoviesRepository;
import com.example.dany.moviesapp.model.Movie;
import com.example.dany.moviesapp.utils.Enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dany on 30.4.2018 г..
 */

public class MoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> listMovies;
    private MoviesRepository moviesRepository;

    private Enums.Filter filter;
    private int lastSearchedRating;
    private int lastSearchedYear;
    private String lastSearchedGenre = "";

    public MoviesViewModel(Application application) {
        super(application);
        moviesRepository = new MoviesRepository(application);
        listMovies = moviesRepository.getListMovies();
        filter = Enums.Filter.ALL;
    }

    public void setFilter(Enums.Filter filter) {
        this.filter = filter;
    }

    public Enums.Filter getFilter() {
        return this.filter;
    }

    public void setLastSearchedRating(int lastSearchedRating) {
        this.lastSearchedRating = lastSearchedRating;
        this.lastSearchedGenre = "";
        this.lastSearchedYear = -1;
    }

    public int getLastSearchedRating() {
        return this.lastSearchedRating;
    }

    public void setLastSearchedYear(int lastSearchedYear) {
        this.lastSearchedYear = lastSearchedYear;
        this.lastSearchedRating = -1;
        this.lastSearchedGenre = "";
    }

    public int getLastSearchedYear() {
        return this.lastSearchedYear;
    }

    public void setLastSearchedGenre(String lastSearchedGenre) {
        this.lastSearchedGenre = lastSearchedGenre;
        this.lastSearchedRating = -1;
        this.lastSearchedYear = -1;
    }

    public String getLastSearchedGenre() {
        return this.lastSearchedGenre;
    }

    public LiveData<List<Movie>> getAllMovies() {
        return listMovies;
    }

    public void insertMovie(Movie movie) {
        moviesRepository.insertMovie(movie);
    }

    public List<Movie> filterMovies(Enums.Filter filter, List<Movie> movies) {
        List<Movie> sortedMovies = new ArrayList<>();
        switch (filter) {
            case ALL:
                sortedMovies = movies;
                break;
            case RATING:
                sortedMovies = getSortedListByRating(movies, lastSearchedRating);
                break;
            case YEAR:
                sortedMovies = getSortedListByYear(movies, lastSearchedYear);
                break;
            case GENRE:
                sortedMovies = getSortedListByGenre(movies, lastSearchedGenre);
                break;
        }
        return sortedMovies;
    }

    private List<Movie> getSortedListByRating(List<Movie> movies, int rating) {
        List<Movie> listSortedMovies = new ArrayList<>();
        for(Movie movie: movies) {
            if(movie.getRating() == rating) {
                listSortedMovies.add(movie);
            }
        }
        return listSortedMovies;
    }

    private List<Movie> getSortedListByYear(List<Movie> movies, int year) {
        List<Movie> listSortedMovies = new ArrayList<>();
        for(Movie movie: movies) {
            if(movie.getYear() == year) {
                listSortedMovies.add(movie);
            }
        }
        return listSortedMovies;
    }

    private List<Movie> getSortedListByGenre(List<Movie> movies, String genre) {
        List<Movie> listSortedMovies = new ArrayList<>();
        for(Movie movie: movies) {
            if(movie.getGenre().equalsIgnoreCase(genre)) {
                listSortedMovies.add(movie);
            }
        }
        return listSortedMovies;
    }

}
