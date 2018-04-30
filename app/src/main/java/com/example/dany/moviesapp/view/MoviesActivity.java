package com.example.dany.moviesapp.view;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dany.moviesapp.R;
import com.example.dany.moviesapp.adapter.MoviesAdapter;
import com.example.dany.moviesapp.model.Movie;
import com.example.dany.moviesapp.utils.Constants;
import com.example.dany.moviesapp.utils.Enums;
import com.example.dany.moviesapp.viewModel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity implements View.OnClickListener{

    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerViewMovies;
    private ImageView imageViewSearch;
    private ImageView imageViewRefresh;
    private TextView textViewEmpty;

    private MoviesViewModel moviesViewModel;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);

        initUI();
        setListeners();

        observeViewModel();
    }

    private void initUI() {
        textViewEmpty = findViewById(R.id.textViewEmpty);
        imageViewSearch = findViewById(R.id.imageViewSearch);
        imageViewRefresh = findViewById(R.id.imageViewRefresh);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        recyclerViewMovies.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewMovies.setLayoutManager(linearLayoutManager);
        adapter = new MoviesAdapter(new ArrayList<Movie>(), this);
        recyclerViewMovies.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewMovies.getContext(),
                linearLayoutManager.getOrientation());
        recyclerViewMovies.addItemDecoration(dividerItemDecoration);
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(this);
        imageViewSearch.setOnClickListener(this);
        imageViewRefresh.setOnClickListener(this);
        recyclerViewMovies.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if (dy > 0 ||dy<0 && floatingActionButton.isShown())
                    floatingActionButton.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    floatingActionButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void observeViewModel() {
        moviesViewModel.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(movies.isEmpty()) {
                    textViewEmpty.setVisibility(View.VISIBLE);
                } else {
                    textViewEmpty.setVisibility(View.GONE);
                }
                adapter.addItems(moviesViewModel.filterMovies(moviesViewModel.getFilter(), movies));
            }
        });
    }

    private void searchByRating() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.menu_search_by_rating));
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        View viewInflated = LayoutInflater.from(this).inflate(R.layout.ratingbar_alert, viewGroup, false);
        final RatingBar ratingBar = viewInflated.findViewById(R.id.ratingBar);
        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(moviesViewModel.getLastSearchedRating() != (int) ratingBar.getRating()) {
                    moviesViewModel.setFilter(Enums.Filter.RATING);
                    moviesViewModel.setLastSearchedRating((int)ratingBar.getRating());
                    showEmptyLabel();
                    adapter.addItems(moviesViewModel.filterMovies(moviesViewModel.getFilter(), moviesViewModel.getAllMovies().getValue()));
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void showEmptyLabel() {
        if(moviesViewModel.filterMovies(moviesViewModel.getFilter(), moviesViewModel.getAllMovies().getValue()).isEmpty()) {
            textViewEmpty.setVisibility(View.VISIBLE);
        } else {
            textViewEmpty.setVisibility(View.GONE);
        }
    }

    private void searchByYear() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.menu_search_by_year));
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!TextUtils.isEmpty(input.getText()) && moviesViewModel.getLastSearchedYear() != Integer.valueOf(input.getText().toString())) {
                    moviesViewModel.setFilter(Enums.Filter.YEAR);
                    moviesViewModel.setLastSearchedYear(Integer.valueOf(input.getText().toString()));
                    showEmptyLabel();
                    adapter.addItems(moviesViewModel.filterMovies(moviesViewModel.getFilter(), moviesViewModel.getAllMovies().getValue()));
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void searchByGenre(String genre) {
        if(!moviesViewModel.getLastSearchedGenre().equalsIgnoreCase(genre)) {
            moviesViewModel.setFilter(Enums.Filter.GENRE);
            moviesViewModel.setLastSearchedGenre(genre);
            showEmptyLabel();
            adapter.addItems(moviesViewModel.filterMovies(moviesViewModel.getFilter(), moviesViewModel.getAllMovies().getValue()));
        }
    }

    private void refresh() {
        if(moviesViewModel.getFilter() != Enums.Filter.ALL) {
            //reset all other filters
            //krupka v koda
            moviesViewModel.setLastSearchedGenre("");
            moviesViewModel.setFilter(Enums.Filter.ALL);
            showEmptyLabel();
            adapter.addItems(moviesViewModel.filterMovies(moviesViewModel.getFilter(), moviesViewModel.getAllMovies().getValue()));
        }
    }

    private void showMenu() {
        PopupMenu popup = new PopupMenu(MoviesActivity.this, imageViewSearch);
        popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.rating:
                        searchByRating();
                        break;
                    case R.id.year:
                        searchByYear();
                        break;
                    case R.id.genre:
                        getFragmentManager().beginTransaction().add(R.id.frame_container, new GenresFragment()).addToBackStack(null).commit();
                        break;
                }
                return true;
            }
        });

        popup.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.NEW_MOVIE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if(bundle != null) {
                Movie movie = (Movie) bundle.getSerializable(Constants.NEW_MOVIE);
                moviesViewModel.insertMovie(movie);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                Intent intentNewMovie = new Intent(MoviesActivity.this, AddMovieActivity.class);
                startActivityForResult(intentNewMovie, Constants.NEW_MOVIE_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.imageViewSearch:
                showMenu();
                break;
            case R.id.imageViewRefresh:
                refresh();
                break;
        }
    }
}
