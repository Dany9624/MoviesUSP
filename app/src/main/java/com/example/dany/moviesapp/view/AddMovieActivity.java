package com.example.dany.moviesapp.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dany.moviesapp.R;
import com.example.dany.moviesapp.model.Movie;
import com.example.dany.moviesapp.utils.Constants;


public class AddMovieActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextName;
    private EditText editTextActors;
    private EditText editTextDirector;
    private EditText editTextYear;
    private TextView textViewGenre;
    private RatingBar ratingBar;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        initUI();
        setListeners();
    }

    private void initUI() {
        editTextName = findViewById(R.id.editTextName);
        editTextActors = findViewById(R.id.editTextActors);
        editTextDirector = findViewById(R.id.editTextDirector);
        editTextYear = findViewById(R.id.editTextYear);
        textViewGenre = findViewById(R.id.textViewGenre);
        ratingBar = findViewById(R.id.ratingBar);
        floatingActionButton = findViewById(R.id.floatingActionButton);
    }

    private void setListeners() {
        floatingActionButton.setOnClickListener(this);
        textViewGenre.setOnClickListener(this);
    }

    private boolean checkForEmptyFields() {
        return (TextUtils.isEmpty(editTextName.getText()) || TextUtils.isEmpty(editTextActors.getText()) ||
                TextUtils.isEmpty(editTextDirector.getText()) || TextUtils.isEmpty(textViewGenre.getText())||
                TextUtils.isEmpty(editTextYear.getText()) || Integer.valueOf(editTextYear.getText().toString()) <= 0 || ratingBar.getRating() == 0);
    }

    public void setGenre(String genre) {
        textViewGenre.setText(genre);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.floatingActionButton:
                if(!checkForEmptyFields() && !textViewGenre.getText().toString().equals("(Select)")) {
                    Intent replyIntent = new Intent(AddMovieActivity.this, MoviesActivity.class);
                    Movie movie = new Movie(String.valueOf(editTextName.getText()), String.valueOf(editTextActors.getText()),
                            String.valueOf(editTextDirector.getText()), Integer.valueOf(editTextYear.getText().toString()),
                            String.valueOf(textViewGenre.getText()) ,(int)ratingBar.getRating());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.NEW_MOVIE, movie);
                    replyIntent.putExtras(bundle);
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
                else {
                    new AlertDialog.Builder(this)
                            .setMessage(getResources().getString(R.string.empty_fields))
                            .setPositiveButton(getResources().getString(R.string.ok), null)
                            .show();
                }
                break;
            case R.id.textViewGenre:
                getFragmentManager().beginTransaction().add(R.id.frame_container, new GenresFragment()).addToBackStack(null).commit();
                break;
        }
    }
}
