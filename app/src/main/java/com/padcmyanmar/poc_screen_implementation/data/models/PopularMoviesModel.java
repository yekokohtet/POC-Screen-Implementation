package com.padcmyanmar.poc_screen_implementation.data.models;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.padcmyanmar.poc_screen_implementation.POCScreenImplApp;
import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;
import com.padcmyanmar.poc_screen_implementation.events.RestApiEvents;
import com.padcmyanmar.poc_screen_implementation.network.PopularMoviesDataAgent;
import com.padcmyanmar.poc_screen_implementation.persistence.MoviesContract;
import com.padcmyanmar.poc_screen_implementation.utils.AppConstants;
import com.padcmyanmar.poc_screen_implementation.utils.ConfigUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by yekokohtet on 12/8/17.
 */

public class PopularMoviesModel {

    @Inject
    PopularMoviesDataAgent mDataAgent;

    @Inject
    ConfigUtils mConfigUtils;

//    private static PopularMoviesModel objInstance;

    private List<PopularMoviesVO> mPopularMovies;
//    private int popularMoviesPageIndex = 1;

    public PopularMoviesModel(Context context) {
        EventBus.getDefault().register(this);
        mPopularMovies = new ArrayList<>();

        POCScreenImplApp mPOCScreenImplApp = (POCScreenImplApp) context.getApplicationContext();
        mPOCScreenImplApp.getPOCAppComponent().inject(this);
    }

//    public static PopularMoviesModel getInstance(Context context) {
//        if (objInstance == null) {
//            objInstance = new PopularMoviesModel(context);
//        }
//        return objInstance;
//    }

    public void startLoadingPopularMovies(Context context) {
        mDataAgent.loadPopularMovies(AppConstants.ACCESS_TOKEN,
                mConfigUtils.loadPageIndex(),
                context);
    }

    public List<PopularMoviesVO> getMovies() {
        return mPopularMovies;
    }

    public void loadMoreMovies(Context context) {
        mDataAgent.loadPopularMovies(AppConstants.ACCESS_TOKEN,
                mConfigUtils.loadPageIndex(),
                context);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onPopularMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        mPopularMovies.addAll(event.getLoadedPopularMovies());

        mConfigUtils.savePageIndex(event.getLoadedPageIndex() + 1);

        //TODO Logic to save the data in Persistence Layer

        ContentValues[] popularMoviesCVS = new ContentValues[event.getLoadedPopularMovies().size()];

        List<ContentValues> genreIdCVList = new ArrayList<>();

        List<ContentValues> popularMovieGenreIdCVList = new ArrayList<>();

        for (int index = 0; index < popularMoviesCVS.length; index++) {
            PopularMoviesVO popularMoviesVO = event.getLoadedPopularMovies().get(index);
            popularMoviesCVS[index] = popularMoviesVO.parseToContentValues();

            for (int genreId : popularMoviesVO.getGenreIds()) {
                ContentValues genreIdCV = new ContentValues();
                genreIdCV.put(MoviesContract.GenreIdEntry.COLUMN_GENRE_ID, genreId);
                genreIdCVList.add(genreIdCV);

                ContentValues popularMovieGenreIdCV = new ContentValues();
                popularMovieGenreIdCV.put(MoviesContract.PopularMovieGenreIdEntry.COLUMN_POPULAR_MOVIE_ID, popularMoviesVO.getId());
                popularMovieGenreIdCV.put(MoviesContract.PopularMovieGenreIdEntry.COLUMN_GENRE_ID, genreId);
                popularMovieGenreIdCVList.add(popularMovieGenreIdCV);
            }
        }

        int insertedPopularMoviesGenreIds = event.getContext().getContentResolver().bulkInsert(MoviesContract.PopularMovieGenreIdEntry.CONTENT_URI,
                popularMovieGenreIdCVList.toArray(new ContentValues[0]));
        Log.d(POCScreenImplApp.LOG_TAG, "Inserted Movies : " + insertedPopularMoviesGenreIds);

        int insertedGenreIds = event.getContext().getContentResolver().bulkInsert(MoviesContract.GenreIdEntry.CONTENT_URI,
                genreIdCVList.toArray(new ContentValues[0]));
        Log.d(POCScreenImplApp.LOG_TAG, "Inserted Movies : " + insertedGenreIds);

        int insertedMovies = event.getContext().getContentResolver().bulkInsert(MoviesContract.PopularMovieEntry.CONTENT_URI, popularMoviesCVS);
        Log.d(POCScreenImplApp.LOG_TAG, "Inserted Movies : " + insertedMovies);
    }

    public void forceRefreshMovies(Context context) {
        mPopularMovies = new ArrayList<>();
//        popularMoviesPageIndex = 1;
        mConfigUtils.savePageIndex(1);
        startLoadingPopularMovies(context);
    }
}
