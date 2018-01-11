package com.padcmyanmar.poc_screen_implementation.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by yekokohtet on 1/3/18.
 */

public class ConfigUtils {

    private static final String KEY_PAGE_INDEX = "KEY_PAGE_INDEX";

//    private static ConfigUtils sObjInstance;

    private SharedPreferences mSharedPreferences;

    public ConfigUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("ConfigUtils", Context.MODE_PRIVATE);
    }

//    public static void initConfigUtils(Context context) {
//        sObjInstance = new ConfigUtils(context);
//    }
//
//    public static ConfigUtils getObjInstance() {
//        return sObjInstance;
//    }

    public void savePageIndex(int pageIndex) {
        mSharedPreferences.edit().putInt(KEY_PAGE_INDEX, pageIndex).apply();
    }

    public int loadPageIndex() {
        return mSharedPreferences.getInt(KEY_PAGE_INDEX, 1);
    }
}
