package com.example.dany.moviesapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dany.moviesapp.R;
import com.example.dany.moviesapp.model.Movie;
import com.example.dany.moviesapp.view.MovieDetailsActivity;

import java.util.List;

/**
 * Created by Dany on 30.4.2018 г..
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Movie> listMovies;
    private Context context;

    public MoviesAdapter(List<Movie>listMovies, Context context) {
        this.listMovies = listMovies;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewName;
        public TextView textViewActors;
        public TextView textViewDirector;
        public TextView textViewYear;
        public TextView textViewGenre;
        public RatingBar ratingBar;

        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            this.textViewName = view.findViewById(R.id.textViewName);
            this.textViewActors = view.findViewById(R.id.textViewActors);
            this.textViewDirector = view.findViewById(R.id.textViewDirector);
            this.textViewYear = view.findViewById(R.id.textViewYear);
            this.textViewGenre = view.findViewById(R.id.textViewGenre);
            this.ratingBar = view.findViewById(R.id.ratingBar);
        }

        @Override
        public void onClick(View view) {
            Intent intentEdit = new Intent(context, MovieDetailsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("id",  listMovies.get(getAdapterPosition()).getId());
            intentEdit.putExtras(bundle);
            context.startActivity(intentEdit);
        }
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_movies_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MoviesAdapter.ViewHolder holder, int position) {
        Movie movie = listMovies.get(position);
        holder.textViewName.setText(context.getResources().getString(R.string.name,movie.getName()));
        holder.textViewActors.setText(context.getResources().getString(R.string.actors, movie.getActors()));
        holder.textViewDirector.setText(context.getResources().getString(R.string.director,movie.getDirector()));
        holder.textViewYear.setText(context.getResources().getString(R.string.year, movie.getYear()));
        holder.textViewGenre.setText(context.getResources().getString(R.string.genre, movie.getGenre()));
        holder.ratingBar.setRating(movie.getRating());
    }

    @Override
    public int getItemCount() {

        if(listMovies != null)
            return listMovies.size();
        else return 0;
    }

    public void addItems(List<Movie> listMovies) {
        this.listMovies = listMovies;
        notifyDataSetChanged();
    }

}
