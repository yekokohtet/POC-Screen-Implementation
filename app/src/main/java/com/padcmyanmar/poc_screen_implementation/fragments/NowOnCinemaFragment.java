package com.padcmyanmar.poc_screen_implementation.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.activities.CinemaImageFullScreenActivity;
import com.padcmyanmar.poc_screen_implementation.adapters.NowOnCinemaAdapter;
import com.padcmyanmar.poc_screen_implementation.delegates.CinemaItemDelegate;
import com.padcmyanmar.poc_screen_implementation.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowOnCinemaFragment extends Fragment implements CinemaItemDelegate{

    RecyclerView rvNowOnCinema;
    CinemaItemDelegate mCinemaItemDelegate;

    private NowOnCinemaAdapter mNowOnCinemaAdapter;

    public NowOnCinemaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_on_cinema, container, false);

        rvNowOnCinema = view.findViewById(R.id.rv_now_on_cinema);
        rvNowOnCinema.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mNowOnCinemaAdapter = new NowOnCinemaAdapter(getContext(), mCinemaItemDelegate);
        rvNowOnCinema.setAdapter(mNowOnCinemaAdapter);

        return view;
    }

    @Override
    public void onTapImageFullScreen() {
        Intent intent = CinemaImageFullScreenActivity.newIntent(getContext());
        startActivity(intent);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPopularMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        mNowOnCinemaAdapter.appendNewData(event.getLoadedPopularMoveis());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvNowOnCinema, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
    }
}
