package com.padcmyanmar.poc_screen_implementation.dagger;

import com.padcmyanmar.poc_screen_implementation.POCScreenImplApp;
import com.padcmyanmar.poc_screen_implementation.activities.MoviesListActivity;
import com.padcmyanmar.poc_screen_implementation.data.models.PopularMoviesModel;
import com.padcmyanmar.poc_screen_implementation.mvp.presenters.NowOnCinemaPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yekokohtet on 1/11/18.
 */

@Component(modules = {POCAppModule.class, NetworkModule.class, UtilsModule.class})
@Singleton
public interface POCAppComponent {

    void inject(POCScreenImplApp app);

    void inject(PopularMoviesModel popularMoviesModel);

    void inject(NowOnCinemaPresenter nowOnCinemaPresenter);

    void inject(MoviesListActivity moviesListActivity);
}
