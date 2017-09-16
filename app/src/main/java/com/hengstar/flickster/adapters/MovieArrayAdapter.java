package com.hengstar.flickster.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hengstar.flickster.R;
import com.hengstar.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.hengstar.flickster.R.id.tvOverview;
import static com.hengstar.flickster.R.id.tvTitle;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the data item for position
        Movie movie = getItem(position);

        ViewHolder viewHolder; // view lookup cache stored in tag

        // check the existing view being reused
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder.ivImage = convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = convertView.findViewById(tvTitle);
            viewHolder.tvOverview = convertView.findViewById(tvOverview);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // clear out image from convertView
        viewHolder.ivImage.setImageResource(0);

        // populate data
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverView());

        Picasso.with(getContext()).load(movie.getPosterPath()).into(viewHolder.ivImage);
        // return the view
        return convertView;
    }
}