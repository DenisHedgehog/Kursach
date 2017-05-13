package com.example.hedgehog.kursach;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by hedgehog on 18.04.17.
 */

class CustomFilmAdapter extends ArrayAdapter<Film> implements View.OnClickListener {

    private ArrayList<Film> getFilms() {
        return films;
    }

    private void setFilms(ArrayList<Film> films) {
        this.films = films;
    }

    private ArrayList<Film> films;
    Context context;

    private static class ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView descriptionTextView;
    }


    CustomFilmAdapter(Context context, ArrayList<Film> films) {
        super(context, R.layout.film_row, films);
        setFilms(films);
        this.context = context;
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    public String toString() {
        return context.toString() + "\n" + getFilms().toString();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView;
        Film film = getItem(position);
        ViewHolder viewHolder;
        rowView = convertView;

        if (rowView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.film_row, parent, false);
            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.row_image_id);
            viewHolder.nameTextView = (TextView) rowView.findViewById(R.id.row_film_name_and_year_id);
            viewHolder.descriptionTextView = (TextView) rowView.findViewById(R.id.row_film_duration_and_genres_id);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        viewHolder.descriptionTextView.setText(film.getDescription());
        viewHolder.nameTextView.setText(film.getName());

        return rowView;
    }
}
