package com.padcmyanmar.poc_screen_implementation.mvp.presenters;

import android.content.Context;
import android.database.Cursor;

import com.padcmyanmar.poc_screen_implementation.POCScreenImplApp;
import com.padcmyanmar.poc_screen_implementation.data.models.PopularMoviesModel;
import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;
import com.padcmyanmar.poc_screen_implementation.delegates.CinemaItemDelegate;
import com.padcmyanmar.poc_screen_implementation.mvp.views.NowOnCinemaView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yekokohtet on 1/10/18.
 */

public class NowOnCinemaPresenter extends BasePresenter implements CinemaItemDelegate {

    @Inject
    PopularMoviesModel mPopularMoviesModel;

    private NowOnCinemaView mView;

    public NowOnCinemaPresenter(Context context, NowOnCinemaView view) {
        mView = view;

        POCScreenImplApp pocScreenImplApp = (POCScreenImplApp) context.getApplicationContext();
        pocScreenImplApp.getPOCAppComponent().inject(this);
    }

    @Override
    public void onStart() {
        List<PopularMoviesVO> moviesList = mPopularMoviesModel.getMovies();
        if (!moviesList.isEmpty()) {
            mView.displayMoviesList(moviesList);
        } else {
            mView.showLoading();
        }
    }

    @Override
    public void onStop() {

    }

    public void onMoviesListEndReach(Context context) {
        mPopularMoviesModel.loadMoreMovies(context);
    }

    public void onForceRefresh(Context context) {
        mPopularMoviesModel.forceRefreshMovies(context);
    }

    public void onDataLoaded(Context context, Cursor data) {
        if (data != null && data.moveToFirst()) {
            List<PopularMoviesVO> moviesList = new ArrayList<>();

            do {
                PopularMoviesVO movie = PopularMoviesVO.parseFromCursor(data, context);
                moviesList.add(movie);
            } while (data.moveToNext());

           mView.displayMoviesList(moviesList);
//            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void onTapMovieOverviewButton(PopularMoviesVO movies) {
        mView.navigateToMovieOverview(movies);
    }

    @Override
    public void onTapImageFullScreenButton() {
//        Toast.makeText(getContext(), "Hey, you clicked the full screen image button!", Toast.LENGTH_SHORT).show();
        mView.navigateToFullPoster();
    }
}
