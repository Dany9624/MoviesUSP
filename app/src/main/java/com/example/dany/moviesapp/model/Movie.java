package com.example.dany.moviesapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Dany on 28.4.2018 г..
 */

@Entity
public class Movie implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    private String name;
    private String actors;
    private String director;
    private int year;
    private String genre;
    private int rating;

    public Movie(String name, String actors, String director, int year, String genre, int rating) {
        this.name = name;
        this.actors = actors;
        this.director = director;
        this.year = year;
        this.genre = genre;
        this.rating = rating;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public String getActors() {
        return this.actors;
    }

    public String getDirector() {
        return this.director;
    }

    public int getYear() {
        return this.year;
    }

    public int getRating() {
        return this.rating;
    }

    public String getGenre() {
        return this.genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

}

