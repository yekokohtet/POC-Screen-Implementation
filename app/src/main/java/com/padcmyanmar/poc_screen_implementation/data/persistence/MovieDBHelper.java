package com.padcmyanmar.poc_screen_implementation.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yekokohtet on 12/14/17.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "movies.db";

    private static final String SQL_CREATE_POPULAR_MOVIE_TABLE = "CREATE TABLE " + MoviesContract.PopularMovieEntry.TABLE_NAME + " (" +
            MoviesContract.PopularMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MoviesContract.PopularMovieEntry.COLUMN_VOTE_COUNT + " INTEGER, " +
            MoviesContract.PopularMovieEntry.COLUMN_POPULAR_MOVIE_ID + " VARCHAR (256), " +
            MoviesContract.PopularMovieEntry.COLUMN_VIDEO + " INTEGER DEFAULT 0, " +
            MoviesContract.PopularMovieEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
            MoviesContract.PopularMovieEntry.COLUMN_TITLE + " TEXT, " +
            MoviesContract.PopularMovieEntry.COLUMN_POPULARITY + " REAL, " +
            MoviesContract.PopularMovieEntry.COLUMN_POSTER_PATH + " TEXT, " +
            MoviesContract.PopularMovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
            MoviesContract.PopularMovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT, " +
            MoviesContract.PopularMovieEntry.COLUMN_BACKDROP_PATH + " TEXT, " +
            MoviesContract.PopularMovieEntry.COLUMN_ADULT + " INTEGER DEFAULT 0, " +
            MoviesContract.PopularMovieEntry.COLUMN_OVERVIEW + " TEXT, " +
            MoviesContract.PopularMovieEntry.COLUMN_RELEASE_DATE + " TEXT, " +

            " UNIQUE (" + MoviesContract.PopularMovieEntry.COLUMN_TITLE + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_GENRE_ID_TABLE = "CREATE TABLE " + MoviesContract.GenreIdEntry.TABLE_NAME + " (" +
            MoviesContract.GenreIdEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MoviesContract.GenreIdEntry.COLUMN_GENRE_ID + " VARCHAR (256), " +

            " UNIQUE (" + MoviesContract.GenreIdEntry.COLUMN_GENRE_ID + ") ON CONFLICT REPLACE" +
            " );";

    private static final String SQL_CREATE_POPLAR_MOVIE_GENRE_ID_TABLE = "CREATE TABLE " + MoviesContract.PopularMovieGenreIdEntry.TABLE_NAME + " (" +
            MoviesContract.PopularMovieGenreIdEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MoviesContract.PopularMovieGenreIdEntry.COLUMN_POPULAR_MOVIE_ID + " VARCHAR (256), " +
            MoviesContract.PopularMovieGenreIdEntry.COLUMN_GENRE_ID + " INTEGER, " +

            " UNIQUE (" + MoviesContract.PopularMovieGenreIdEntry.COLUMN_GENRE_ID + ", " +
            MoviesContract.PopularMovieGenreIdEntry.COLUMN_POPULAR_MOVIE_ID + ") ON CONFLICT REPLACE" +
            " );";

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_POPULAR_MOVIE_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_GENRE_ID_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_POPLAR_MOVIE_GENRE_ID_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.PopularMovieEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.GenreIdEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.PopularMovieGenreIdEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
