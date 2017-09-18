package com.hengstar.flickster.activities;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hengstar.flickster.R;
import com.hengstar.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    @BindView(R.id.ivMovieImage) ImageView ivImage;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.rbRating) RatingBar rbRating;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Movie movie = (Movie) getIntent().getSerializableExtra("movie_info");
        populateData(movie);
    }

    private void populateData(Movie movie) {
        // populate data
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setText(movie.getOverView());

        String posterPath = movie.getBackdropPath();

        @DrawableRes int placeholder = R.drawable.placeholder_landscape;
        Picasso.with(this)
                .load(posterPath)
                .placeholder(placeholder)
                .into(ivImage);

        rbRating.setRating(movie.getStarNum());
        tvReleaseDate.setText(movie.getReleaseData());
    }
}
