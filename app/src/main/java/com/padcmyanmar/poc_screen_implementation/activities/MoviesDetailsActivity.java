package com.padcmyanmar.poc_screen_implementation.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.adapters.MoviesTrailerAdapter;
import com.padcmyanmar.poc_screen_implementation.components.EmptyViewPod;
import com.padcmyanmar.poc_screen_implementation.components.SmartRecyclerView;
import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;
import com.padcmyanmar.poc_screen_implementation.persistence.MoviesContract;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yekokohtet on 12/12/17.
 */

public class MoviesDetailsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int MOVIE_DETAILS_LOADER_ID = 1002;

    private static final String IE_MOVIE_ID = "IE_MOVIE_ID";

    private String mMovieId;

    @BindView(R.id.rv_trailer_movie)
    SmartRecyclerView rvTrailerMovie;

    @BindView(R.id.vp_empty_movies_trailer)
    EmptyViewPod vpEmptyMoviesTrailer;

    @BindView(R.id.iv_movie_poster)
    ImageView ivMoviePoster;

    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;

    @BindView(R.id.tv_popularity)
    TextView tvPopularity;

    @BindView(R.id.tv_movie_review)
    TextView tvMovieReview;

    @BindView(R.id.tv_released_date)
    TextView tvReleasedDate;

    public static Intent newIntent(Context context, String moviesId) {
        Intent intent = new Intent(context, MoviesDetailsActivity.class);
        intent.putExtra(IE_MOVIE_ID, moviesId);
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

        mMovieId = getIntent().getStringExtra(IE_MOVIE_ID);
//        Log.d(POCScreenImplApp.LOG_TAG,mMovieId+" Movie ID");

        if (TextUtils.isEmpty(mMovieId)) {
            throw new UnsupportedOperationException("mMovieId required for MoviesDetailsActivity");
        } else {
            getSupportLoaderManager().initLoader(MOVIE_DETAILS_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getApplicationContext(),
                MoviesContract.PopularMovieEntry.CONTENT_URI,
                null,
                MoviesContract.PopularMovieEntry.COLUMN_POPULAR_MOVIE_ID + " = ?", new String[]{mMovieId},
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst()) {
            PopularMoviesVO movie = PopularMoviesVO.parseFromCursor(data, getApplicationContext());
            bindData(movie);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void bindData(PopularMoviesVO movie) {

        tvMovieTitle.setText(movie.getTitle());
        tvPopularity.setText(String.valueOf(movie.getVoteAverage()));

        Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/original" + movie.getPosterPath())
                .into(ivMoviePoster);

        tvMovieReview.setText(movie.getOverview());

        String releasedDate = "Released : " + movie.getReleaseDate();
        tvReleasedDate.setText(releasedDate);
    }
}
