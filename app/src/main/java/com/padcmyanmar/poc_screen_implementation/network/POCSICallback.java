package com.padcmyanmar.poc_screen_implementation.network;

import com.padcmyanmar.poc_screen_implementation.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yekokohtet on 12/9/17.
 */

public abstract class POCSICallback<T extends POCSIResponse> implements Callback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        POCSIResponse pocsiResponse = response.body();
        if (pocsiResponse == null) {
            RestApiEvents.ErrorInvokingAPIEvent errorEvent
                    = new RestApiEvents.ErrorInvokingAPIEvent("No data could be loaded for now. Pls try again later.");
            EventBus.getDefault().post(errorEvent);
        }

    }

    @Override
    public void onFailure(Call call, Throwable t) {
        RestApiEvents.ErrorInvokingAPIEvent errorEvent = new RestApiEvents.ErrorInvokingAPIEvent(t.getMessage());
        EventBus.getDefault().post(errorEvent);
    }
}
