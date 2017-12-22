package com.padcmyanmar.poc_screen_implementation.data.vo;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;
import com.padcmyanmar.poc_screen_implementation.data.persistence.MoviesContract;

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

    public static PopularMoviesVO parseFromCursor(Cursor cursor) {

        PopularMoviesVO popularMovies = new PopularMoviesVO();

        popularMovies.voteAverage = cursor.getInt(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_VOTE_AVERAGE));
        popularMovies.title = cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_ORIGINAL_TITLE));
        popularMovies.popularity = cursor.getFloat(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_POPULARITY));
        popularMovies.posterPath = cursor.getString(cursor.getColumnIndex(MoviesContract.PopularMovieEntry.COLUMN_POSTER_PATH));

        return popularMovies;
    }
}
