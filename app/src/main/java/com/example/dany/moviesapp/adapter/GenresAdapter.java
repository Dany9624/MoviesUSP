package com.example.dany.moviesapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dany.moviesapp.R;

/**
 * Created by Dany on 2.5.2018 г..
 */

public class GenresAdapter extends BaseAdapter {

    private Context context;
    private String[] genres;

    public GenresAdapter(Context context, String[] genres) {
        this.context = context;
        this.genres = genres;
    }

    @Override
    public int getCount() {
        return genres.length;
    }

    @Override
    public Object getItem(int position) {
        return genres[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if(convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.genres_listview_item, viewGroup, false);
        }
        TextView textViewGenre = convertView.findViewById(R.id.textViewGenre);
        textViewGenre.setText(genres[position]);
        return convertView;
    }
}

