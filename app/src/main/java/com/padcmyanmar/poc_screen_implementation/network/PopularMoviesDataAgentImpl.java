package com.padcmyanmar.poc_screen_implementation.network;

import com.google.gson.Gson;
import com.padcmyanmar.poc_screen_implementation.events.RestApiEvents;
import com.padcmyanmar.poc_screen_implementation.network.responses.GetPopularMoviesResponse;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yekokohtet on 12/8/17.
 */

public class PopularMoviesDataAgentImpl implements PopularMoviesDataAgent{

    private static PopularMoviesDataAgentImpl objInstance;

    private PopularMoviesAPI theAPI;

    private PopularMoviesDataAgentImpl() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://padcmyanmar.com/padc-3/popular-movies/apis/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient)
                .build();

        theAPI = retrofit.create(PopularMoviesAPI.class);
    }

    public static PopularMoviesDataAgentImpl getInstance() {
        if (objInstance == null) {
            objInstance = new PopularMoviesDataAgentImpl();
        }
        return objInstance;
    }

    @Override
    public void loadPopularMovies(String accessToken, final int pageNo) {

        Call<GetPopularMoviesResponse> loadPopularMoviesCall = theAPI.loadPopularMovies(accessToken, pageNo);

        loadPopularMoviesCall.enqueue(new Callback<GetPopularMoviesResponse>() {
            @Override
            public void onResponse(Call<GetPopularMoviesResponse> call, Response<GetPopularMoviesResponse> response) {
                GetPopularMoviesResponse getPopularMoviesResponse = response.body();
                if (getPopularMoviesResponse != null && getPopularMoviesResponse.getPopularMoviesList().size() > 0) {
                    RestApiEvents.MoviesDataLoadedEvent moviesDataLoadedEvent = new RestApiEvents.MoviesDataLoadedEvent(getPopularMoviesResponse.getPage(), getPopularMoviesResponse.getPopularMoviesList());
                    EventBus.getDefault().post(moviesDataLoadedEvent);
                } else {
                    RestApiEvents.ErrorInvokingAPIEvent errorEvent = new RestApiEvents.ErrorInvokingAPIEvent("No data could be loaded for now. Pls try again later.");
                    EventBus.getDefault().post(errorEvent);
                }
            }

            @Override
            public void onFailure(Call<GetPopularMoviesResponse> call, Throwable t) {
                RestApiEvents.ErrorInvokingAPIEvent errorEvent = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
                EventBus.getDefault().post(errorEvent);
            }
        });
    }
}