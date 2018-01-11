package com.padcmyanmar.poc_screen_implementation.mvp.presenters;

/**
 * Created by yekokohtet on 1/10/18.
 */

public abstract class BasePresenter {

    public void onCreateView() {}

    public void onCreate() {}

    public abstract void onStart();

    public void onResume() {}

    public void onPause() {}

    public abstract void onStop();

    public void onDestroy() {}

    public void onDestroyView() {}
}
