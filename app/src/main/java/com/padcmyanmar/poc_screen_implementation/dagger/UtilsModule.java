package com.padcmyanmar.poc_screen_implementation.dagger;

import android.content.Context;

import com.padcmyanmar.poc_screen_implementation.utils.ConfigUtils;

import dagger.Module;
import dagger.Provides;

/**
 * Created by yekokohtet on 1/11/18.
 */

@Module
public class UtilsModule {

    @Provides
    public ConfigUtils provideConfigUtils(Context context) {
        return new ConfigUtils(context);
    }

}
