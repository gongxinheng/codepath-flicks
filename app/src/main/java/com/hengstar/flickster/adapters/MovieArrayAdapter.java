package com.hengstar.flickster.adapters;


import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.DrawableRes;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    static class ViewHolder {
        @BindView(R.id.ivMovieImage) ImageView ivImage;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
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
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            viewHolder = new ViewHolder(convertView);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // clear out image from convertView
        viewHolder.ivImage.setImageResource(0);

        int orientation = convertView.getResources().getConfiguration().orientation;
        populateData(viewHolder, movie, orientation == Configuration.ORIENTATION_LANDSCAPE);

        // return the view
        return convertView;
    }

    private void populateData(ViewHolder viewHolder, Movie movie, boolean landscape) {
        // populate data
        viewHolder.tvTitle.setText(movie.getOriginalTitle());
        viewHolder.tvOverview.setText(movie.getOverView());

        String posterPath = landscape ? movie.getBackdropPath() : movie.getPosterPath();

        @DrawableRes int placeholder = landscape ? R.drawable.placeholder_landscape : R.drawable.placeholder_portrait;
        Picasso.with(getContext())
                .load(posterPath)
                .placeholder(placeholder)
                .fit()
                .transform(new RoundedCornersTransformation(20, 20))
                .into(viewHolder.ivImage);
    }
}
