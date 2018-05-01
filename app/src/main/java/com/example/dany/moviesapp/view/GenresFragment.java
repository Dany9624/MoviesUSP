package com.example.dany.moviesapp.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dany.moviesapp.R;
import com.example.dany.moviesapp.adapter.GenresAdapter;

public class GenresFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener{

    private ListView listViewGenres;
    private ImageView imageViewClose;

    public GenresFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_genres, container, false);
        initUI(rootView);
        setListeners();
        return rootView;
    }

    private void initUI(View rootView) {
        imageViewClose = rootView.findViewById(R.id.imageViewClose);
        listViewGenres = rootView.findViewById(R.id.listViewGenres);
        GenresAdapter adapter = new GenresAdapter(getActivity(), getResources().getStringArray(R.array.arr_genres));
        listViewGenres.setAdapter(adapter);
    }

    private void setListeners() {
        imageViewClose.setOnClickListener(this);
        listViewGenres.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TextView textViewGenre = view.findViewById(R.id.textViewGenre);
        String genre = textViewGenre.getText().toString();
        if(getActivity() instanceof AddMovieActivity) {
            ((AddMovieActivity)getActivity()).setGenre(genre);
        }
        else if(getActivity() instanceof MovieDetailsActivity) {
            ((MovieDetailsActivity) getActivity()).setGenre(genre);
        }
        else if((getActivity()) instanceof MoviesActivity) {
            ((MoviesActivity)getActivity()).searchByGenre(genre);
        }
        getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageViewClose:
                getFragmentManager().popBackStack();
                break;
        }
    }
}

