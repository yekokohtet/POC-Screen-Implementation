package com.padcmyanmar.poc_screen_implementation.network;

import com.padcmyanmar.poc_screen_implementation.network.responses.GetPopularMoviesResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yekokohtet on 12/8/17.
 */

public interface PopularMoviesAPI {

    @FormUrlEncoded
    @POST("v1/getPopularMovies.php")
    Call<GetPopularMoviesResponse> loadPopularMovies(
            @Field("access_token") String accessToken,
            @Field("page") int pageIndex
    );
}
