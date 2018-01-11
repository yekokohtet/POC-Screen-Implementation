package com.padcmyanmar.poc_screen_implementation.dagger;

import com.padcmyanmar.poc_screen_implementation.network.PopularMoviesDataAgent;
import com.padcmyanmar.poc_screen_implementation.network.PopularMoviesDataAgentImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yekokohtet on 1/11/18.
 */

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public PopularMoviesDataAgent providePopularMoviesDataAgent() {
        return new PopularMoviesDataAgentImpl();
    }
}
