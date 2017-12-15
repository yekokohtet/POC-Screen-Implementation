package com.padcmyanmar.poc_screen_implementation.events;

import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;

import java.util.List;

/**
 * Created by yekokohtet on 12/8/17.
 */

public class RestApiEvents {

    public static class EmptyResponseEvent {

    }

    public static class ErrorInvokingAPIEvent {

        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class MoviesDataLoadedEvent {

        private int loadedPageIndex;
        private List<PopularMoviesVO> loadedPopularMovies;

        public MoviesDataLoadedEvent(int loadedPageIndex, List<PopularMoviesVO> loadedPopularMovies) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedPopularMovies = loadedPopularMovies;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public List<PopularMoviesVO> getLoadedPopularMovies() {
            return loadedPopularMovies;
        }
    }
}
