package com.padcmyanmar.poc_screen_implementation.delegates;

import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;

/**
 * Created by yekokohtet on 11/12/17.
 */

public interface CinemaItemDelegate {

    void onTapMovieOverviewButton(PopularMoviesVO movies);
    void onTapImageFullScreenButton();
}
