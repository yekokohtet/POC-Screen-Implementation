package com.padcmyanmar.poc_screen_implementation.persistence;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by yekokohtet on 12/20/17.
 */

public class MoviesProvider extends ContentProvider {

    public static final int POPULAR_MOVIES = 100;
    public static final int GENRE_IDS = 200;
    public static final int POPULAR_MOVIES_GENRE_IDS = 300;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final SQLiteQueryBuilder sGenreIDsWithPopularMoviesGenreIDs_IJ;

    static {
        sGenreIDsWithPopularMoviesGenreIDs_IJ = new SQLiteQueryBuilder();
        sGenreIDsWithPopularMoviesGenreIDs_IJ.setTables(
                MoviesContract.GenreIdEntry.TABLE_NAME + " INNER JOIN " +
                        MoviesContract.PopularMovieGenreIdEntry.TABLE_NAME + " ON " +
                        MoviesContract.GenreIdEntry.TABLE_NAME + " . " + MoviesContract.GenreIdEntry.COLUMN_GENRE_ID + " = " +
                        MoviesContract.PopularMovieGenreIdEntry.TABLE_NAME + " . " + MoviesContract.PopularMovieGenreIdEntry.COLUMN_GENRE_ID
        );
    }

    private MovieDBHelper mDBHelper;

    @Override
    public boolean onCreate() {
        mDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Cursor queryCursor;
        switch (sUriMatcher.match(uri)) {
            case POPULAR_MOVIES_GENRE_IDS:
                queryCursor = sGenreIDsWithPopularMoviesGenreIDs_IJ.query(mDBHelper.getReadableDatabase(),
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                break;

            default:
                queryCursor = mDBHelper.getReadableDatabase().query(getTableName(uri),
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
        }

        if (getContext() != null) {
            queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return queryCursor;
    }

    /**
     * getType method က parse လုပ္​ေပးလုိက္တဲ့ uri ​ေပၚမူတည္ၿပီး
     * အဲဒီ uri က​​ေန query လုပ္လုိက္ရင္ single data return ျပန္မွာလား
     * collection return ျပန္မွာလား ဆိုတာ စစ္​ေပးတာ
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case POPULAR_MOVIES:
                return MoviesContract.PopularMovieEntry.DIR_TYPE;
            case GENRE_IDS:
                return MoviesContract.GenreIdEntry.DIR_TYPE;
            case POPULAR_MOVIES_GENRE_IDS:
                return MoviesContract.PopularMovieGenreIdEntry.DIR_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        long _id = db.insert(tableName, null, contentValues);
        if (_id > 0) {
            Uri tableContentUri = getContentUri(uri);
            Uri insertedUri = ContentUris.withAppendedId(tableContentUri, _id);

            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            return insertedUri;
        }
        return null;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        String tableName = getTableName(uri);
        int insertedCount = 0;

        try {
            db.beginTransaction();
            for (ContentValues cv : values) {
                long _id = db.insert(tableName, null, cv);
                if (_id > 0) {
                    insertedCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }

        Context context = getContext();
        if (context != null) {
            context.getContentResolver().notifyChange(uri, null);
        }

        return insertedCount;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rowDeleted;
        String tableName = getTableName(uri);

        rowDeleted = db.delete(tableName, s, strings);
        Context context = getContext();
        if (context != null && rowDeleted > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int rowUpdated;
        String tableName = getTableName(uri);

        rowUpdated = db.update(tableName, contentValues, s, strings);
        Context context = getContext();
        if (context != null && rowUpdated > 0) {
            context.getContentResolver().notifyChange(uri, null);
        }
        return rowUpdated;
    }

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_POPULAR_MOVIES, POPULAR_MOVIES);
        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_GENRE_IDS, GENRE_IDS);
        uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY, MoviesContract.PATH_POPULAR_MOVIES_GENRE_IDS, POPULAR_MOVIES_GENRE_IDS);

        return uriMatcher;
    }

    private String getTableName(Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case POPULAR_MOVIES:
                return MoviesContract.PopularMovieEntry.TABLE_NAME;
            case GENRE_IDS:
                return MoviesContract.GenreIdEntry.TABLE_NAME;
            case POPULAR_MOVIES_GENRE_IDS:
                return  MoviesContract.PopularMovieGenreIdEntry.TABLE_NAME;
        }
        return null;
    }

    private Uri getContentUri(Uri uri) {

        switch (sUriMatcher.match(uri)) {
            case POPULAR_MOVIES:
                return MoviesContract.PopularMovieEntry.CONTENT_URI;
            case GENRE_IDS:
                return MoviesContract.GenreIdEntry.CONTENT_URI;
            case POPULAR_MOVIES_GENRE_IDS:
                return MoviesContract.PopularMovieGenreIdEntry.CONTENT_URI;
        }
        return null;
    }

}
