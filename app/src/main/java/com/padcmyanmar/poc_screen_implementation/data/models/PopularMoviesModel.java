package com.padcmyanmar.poc_screen_implementation.data.models;

import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;
import com.padcmyanmar.poc_screen_implementation.events.RestApiEvents;
import com.padcmyanmar.poc_screen_implementation.network.PopularMoviesDataAgentImpl;
import com.padcmyanmar.poc_screen_implementation.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yekokohtet on 12/8/17.
 */

public class PopularMoviesModel {

    private static PopularMoviesModel objInstance;

    private List<PopularMoviesVO> mPopularMovies;
    private int popularMoviesPageIndex = 1;

    private PopularMoviesModel() {
        EventBus.getDefault().register(this);
        mPopularMovies = new ArrayList<>();
    }

    public static PopularMoviesModel getInstance() {
        if (objInstance == null) {
            objInstance = new PopularMoviesModel();
        }
        return objInstance;
    }

    public void startLoadingPopularMovies() {
        PopularMoviesDataAgentImpl.getInstance().loadPopularMovies(AppConstants.ACCESS_TOKEN, popularMoviesPageIndex);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onPopularMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        mPopularMovies.addAll(event.getLoadedPopularMoveis());
        popularMoviesPageIndex = event.getLoadedPageIndex() + 1;
    }
}
