package com.padcmyanmar.poc_screen_implementation.data.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yekokohtet on 12/14/17.
 */

public class MoivieDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";

    private static final String SQL_CREATE_POPULAR_MOVIE_TABLE = "CREATE TABLE " + MoviesContract.PopularMovieEntry.TABLE_NAME + " (" +
            MoviesContract.PopularMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MoviesContract.PopularMovieEntry.COLUMN_VOTE_COUNT + "INTEGER NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_ID + "INTEGER NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_VIDEO + "BOOLEAN NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_VOTE_AVERAGE + "FLOAT NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_TITLE + "TEXT NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_POPULARITY + "FLOAT NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_POSTER_PATH + "TEXT NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_ORIGINAL_LANGUAGE + "TEXT NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_ORIGNINAL_TITLE + "TEXT NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_BACKDROP_PATH + "TEXT NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_ADULT + "BOOLEAN NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_OVERVIEW + "TEXT NOT NULL, " +
            MoviesContract.PopularMovieEntry.COLUMN_RELEASE_DATE + "TEXT NOT NULL, " +

            " UNIQUE (" + MoviesContract.PopularMovieEntry.COLUMN_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_GENRE_ID_TABLE = "CREATE TABLE " + MoviesContract.GenreIdEntry.TABLE_NAME + " (" +
            MoviesContract.GenreIdEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MoviesContract.GenreIdEntry.COLUMN_GENRE_ID + "INTEGER NOT NULL, " +

            " UNIQUE (" + MoviesContract.GenreIdEntry.COLUMN_GENRE_ID + ") ON CONFLICT IGNORE" +
            " );";

    private static final String SQL_CREATE_POPLAR_MOVIE_GENRE_ID_TABLE = "CREATE TABLE " + MoviesContract.PopularMovieGenreIdEntry.TABLE_NAME + " (" +
            MoviesContract.PopularMovieGenreIdEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MoviesContract.PopularMovieGenreIdEntry.COLUMN_POPULAR_MOVIE_TITLE + "INTEGER NOT NULL, " +
            MoviesContract.PopularMovieGenreIdEntry.COLUMN_GENRE_ID + "INTEGER NOT NULL, " +

            " UNIQUE (" + MoviesContract.PopularMovieGenreIdEntry.COLUMN_GENRE_ID + ", " +
            MoviesContract.PopularMovieGenreIdEntry.COLUMN_POPULAR_MOVIE_TITLE + ") ON CONFLICT IGNORE" +
            " );";

    public MoivieDBHelper(Context context) {
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
