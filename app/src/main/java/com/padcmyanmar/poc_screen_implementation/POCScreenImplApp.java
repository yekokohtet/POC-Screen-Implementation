package com.padcmyanmar.poc_screen_implementation;

import android.app.Application;
import android.content.Context;

import com.padcmyanmar.poc_screen_implementation.dagger.DaggerPOCAppComponent;
import com.padcmyanmar.poc_screen_implementation.dagger.NetworkModule;
import com.padcmyanmar.poc_screen_implementation.dagger.POCAppComponent;
import com.padcmyanmar.poc_screen_implementation.dagger.POCAppModule;

import javax.inject.Inject;

/**
 * Created by yekokohtet on 11/7/17.
 */

public class POCScreenImplApp extends Application {

    public static final String LOG_TAG = "POCScreenImplApp";

    private POCAppComponent mPOCAppComponent;

    @Inject
    Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mPOCAppComponent = initDagger();
        mPOCAppComponent.inject(this);

//        ConfigUtils.initConfigUtils(mContext);
    }

    private POCAppComponent initDagger() {
        return DaggerPOCAppComponent.builder()
                .pOCAppModule(new POCAppModule(this))
                .networkModule(new NetworkModule())
                .build();
    }

    public POCAppComponent getPOCAppComponent() {
        return mPOCAppComponent;
    }
}
