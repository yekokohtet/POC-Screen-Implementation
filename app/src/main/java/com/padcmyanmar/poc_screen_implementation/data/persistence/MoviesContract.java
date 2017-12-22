package com.padcmyanmar.poc_screen_implementation.data.persistence;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.padcmyanmar.poc_screen_implementation.POCScreenImplApp;

/**
 * Created by yekokohtet on 12/12/17.
 */

public class MoviesContract {

    public static final String CONTENT_AUTHORITY = POCScreenImplApp.class.getPackage().getName();
    //com.padcmyanmar.poc_screen_implementation

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //content://com.padcmyanmar.poc_screen_implementation

    public static final String PATH_POPULAR_MOVIES = "popular_movies";
    public static final String PATH_GENRE_IDS = "genre_ids";
    public static final String PATH_POPULAR_MOVIES_GENRE_IDS = "popular_movies_genre_ids";

    public static final class PopularMovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR_MOVIES).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIES;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIES;

        public static final String TABLE_NAME = PATH_POPULAR_MOVIES;

        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_POPULAR_MOVIE_ID = "id";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_ADULT = "adult";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static Uri buildPopularMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildPopularMovieUriWithTitle(String popularMovieTitle) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(COLUMN_TITLE, popularMovieTitle)
                    .build();
        }

        public static String getTitleFromParam(Uri uri) {
            return uri.getQueryParameter(COLUMN_TITLE);
        }
    }

    public static final class GenreIdEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_GENRE_IDS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRE_IDS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_GENRE_IDS;

        public static final String TABLE_NAME = PATH_GENRE_IDS;

        public static final String COLUMN_GENRE_ID = "genre_id";

        public static Uri buildGenreIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class PopularMovieGenreIdEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR_MOVIES_GENRE_IDS).build();

        public static final String DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIES_GENRE_IDS;

        public static final String ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIES_GENRE_IDS;

        public static final String TABLE_NAME = PATH_POPULAR_MOVIES_GENRE_IDS;

        public static final String COLUMN_POPULAR_MOVIE_ID = "id";
        public static final String COLUMN_GENRE_ID = "genre_id";

        public static Uri buildPopularMovieGenreIdUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}

