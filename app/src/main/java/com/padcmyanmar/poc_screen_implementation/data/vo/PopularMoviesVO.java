package com.padcmyanmar.poc_screen_implementation.data.vo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.padcmyanmar.poc_screen_implementation.persistence.MoviesContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yekokohtet on 12/8/17.
 */

public class PopularMoviesVO {

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("id")
    private int id;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("title")
    private String title;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    public int getVoteCount() {
        return voteCount;
    }

    public int getId() {
        return id;
    }

    public boolean isVideo() {
        return video;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public ContentValues parseToContentValues() {

        ContentValues contentValues = new ContentValues();

        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_VOTE_COUNT, voteCount);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_POPULAR_MOVIE_ID, id);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_VIDEO, video);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_VOTE_AVERAGE, voteAverage);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_TITLE, title);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_POPULARITY, popularity);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_POSTER_PATH, posterPath);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_ORIGINAL_LANGUAGE, originalLanguage);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_ORIGINAL_TITLE, originalTitle);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_BACKDROP_PATH, backdropPath);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_ADULT, adult);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_OVERVIEW, overview);
        contentValues.put(MoviesContract.PopularMovieEntry.COLUMN_RELEASE_DATE, releaseDate);

        return contentValues;
    }

    public static PopularMoviesVO parseFromCursor(Cursor cursor, Context context) {

        PopularMoviesVO popularMovies = new PopularMoviesVO();

        popularMovies.voteCount = cursor.getInt(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_VOTE_COUNT));
        popularMovies.id = cursor.getInt(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_POPULAR_MOVIE_ID));
        popularMovies.video = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_VIDEO)));
        popularMovies.voteAverage = cursor.getInt(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_VOTE_AVERAGE));
        popularMovies.title = cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_ORIGINAL_TITLE));
        popularMovies.popularity = cursor.getFloat(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_POPULARITY));
        popularMovies.posterPath = cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_POSTER_PATH));
        popularMovies.originalLanguage = cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_ORIGINAL_LANGUAGE));
        popularMovies.originalTitle = cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_ORIGINAL_TITLE));
        popularMovies.backdropPath = cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_BACKDROP_PATH));
        popularMovies.adult = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_ADULT)));
        popularMovies.overview = cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_OVERVIEW));
        popularMovies.releaseDate = cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_RELEASE_DATE));

        popularMovies.genreIds = loadGenreIds(context, popularMovies.id);

        return popularMovies;
    }

    private static List<Integer> loadGenreIds(Context context, int id) {

        Cursor genresInPopularMovieCursor = context.getContentResolver().query(MoviesContract.PopularMovieGenreIdEntry.CONTENT_URI,
                null,
                MoviesContract.PopularMovieGenreIdEntry.COLUMN_POPULAR_MOVIE_ID + " = ?", new String[]{String.valueOf(id)},
                null);

        if (genresInPopularMovieCursor != null && genresInPopularMovieCursor.moveToFirst()) {
            List<Integer> genresInPopularMovie = new ArrayList<>();
            do {
                genresInPopularMovie.add(
                        genresInPopularMovieCursor.getInt((genresInPopularMovieCursor.getColumnIndex(MoviesContract.PopularMovieGenreIdEntry.COLUMN_GENRE_ID)))
                );
            } while (genresInPopularMovieCursor.moveToNext());
            genresInPopularMovieCursor.close();
            return genresInPopularMovie;
        }
        return null;
    }
}
