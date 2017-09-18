package com.hengstar.flickster.adapters;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hengstar.flickster.R;
import com.hengstar.flickster.activities.MovieDetailActivity;
import com.hengstar.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    static class ViewHolder {
        @BindView(R.id.rlItemMovie) RelativeLayout rlItemMovie;
        @BindView(R.id.ivMovieImage) ImageView ivImage;
        @BindView(R.id.ivPlay) ImageView ivPlay;
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    // the threshold that a movie should be marked as popular
    public static double POPULARITY_THRESHOLD = 250;

    public MovieArrayAdapter(Context context, List<Movie> movies) {
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get the data item for position
        final Movie movie = getItem(position);

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

        final Context context = convertView.getContext();

        // clear out image from convertView
        viewHolder.ivImage.setImageResource(0);
        viewHolder.rlItemMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // first parameter is the context, second is the class of the activity to launch
                Intent i = new Intent(context, MovieDetailActivity.class);
                // put "extras" into the bundle for access in the second activity
                i.putExtra("movie_info", movie);
                context.startActivity(i);
            }
        });

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

        // set play icon as foreground if it is popular
        if (movie.getPopularity() > POPULARITY_THRESHOLD) {
            viewHolder.ivPlay.setVisibility(View.VISIBLE);
        } else {
            viewHolder.ivPlay.setVisibility(View.GONE);
        }
    }
}
