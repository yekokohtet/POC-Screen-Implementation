package com.padcmyanmar.poc_screen_implementation.network;

import android.content.Context;

/**
 * Created by yekokohtet on 12/8/17.
 */

public interface PopularMoviesDataAgent {

    void loadPopularMovies(String accessToken, int pageNo, Context context);
}
