package com.padcmyanmar.poc_screen_implementation.mvp.views;

import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;

import java.util.List;

/**
 * Created by yekokohtet on 1/10/18.
 */

public interface NowOnCinemaView {

    void displayMoviesList(List<PopularMoviesVO> moviesList);

    void showLoading();

    void navigateToMovieOverview(PopularMoviesVO movies);

    void navigateToFullPoster();

}
