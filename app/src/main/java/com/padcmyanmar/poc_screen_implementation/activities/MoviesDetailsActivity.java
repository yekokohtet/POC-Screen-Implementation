package com.padcmyanmar.poc_screen_implementation.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.adapters.MoviesTrailerAdapter;
import com.padcmyanmar.poc_screen_implementation.components.EmptyViewPod;
import com.padcmyanmar.poc_screen_implementation.components.SmartRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yekokohtet on 12/12/17.
 */

public class MoviesDetailsActivity extends AppCompatActivity {

    @BindView(R.id.rv_trailer_movie)
    SmartRecyclerView rvTrailerMovie;

    @BindView(R.id.vp_empty_movies_trailer)
    EmptyViewPod vpEmptyMoviesTrailer;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MoviesDetailsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        ButterKnife.bind(this, this);

        rvTrailerMovie.setmEmptyView(vpEmptyMoviesTrailer);
        rvTrailerMovie.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        MoviesTrailerAdapter moviesTrailerAdapter = new MoviesTrailerAdapter(getApplicationContext());
        rvTrailerMovie.setAdapter(moviesTrailerAdapter);
    }
}
