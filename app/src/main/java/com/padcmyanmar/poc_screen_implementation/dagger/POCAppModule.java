package com.padcmyanmar.poc_screen_implementation.dagger;

import android.content.Context;

import com.padcmyanmar.poc_screen_implementation.POCScreenImplApp;
import com.padcmyanmar.poc_screen_implementation.data.models.PopularMoviesModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yekokohtet on 1/11/18.
 */

@Module
public class POCAppModule {

    private POCScreenImplApp mApp;

    public POCAppModule(POCScreenImplApp application) {
        mApp = application;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mApp.getApplicationContext();
    }

    @Provides
    @Singleton
    public PopularMoviesModel providePopularMoviesModel(Context context) {
        return new PopularMoviesModel(context);
    }

}
