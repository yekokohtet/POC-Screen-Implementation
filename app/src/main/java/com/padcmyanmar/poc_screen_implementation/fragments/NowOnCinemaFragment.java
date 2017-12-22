package com.padcmyanmar.poc_screen_implementation.fragments;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.adapters.NowOnCinemaAdapter;
import com.padcmyanmar.poc_screen_implementation.components.EmptyViewPod;
import com.padcmyanmar.poc_screen_implementation.components.SmartRecyclerView;
import com.padcmyanmar.poc_screen_implementation.components.SmartScrollListener;
import com.padcmyanmar.poc_screen_implementation.data.models.PopularMoviesModel;
import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;
import com.padcmyanmar.poc_screen_implementation.delegates.CinemaItemDelegate;
import com.padcmyanmar.poc_screen_implementation.events.RestApiEvents;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowOnCinemaFragment extends Fragment implements CinemaItemDelegate {

    SmartRecyclerView rvNowOnCinema;
    CinemaItemDelegate mCinemaItemDelegate;
    EmptyViewPod vpEmptyViewPod;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private NowOnCinemaAdapter mNowOnCinemaAdapter;

    private SmartScrollListener mSmartScrollListener;

    public NowOnCinemaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        List<PopularMoviesVO> moviesList = PopularMoviesModel.getInstance().getMovies();
        if (!moviesList.isEmpty()) {
            mNowOnCinemaAdapter.setNewData(moviesList);
        } else {
            swipeRefreshLayout.setRefreshing(true);
        }
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

        ButterKnife.bind(this, view);

        vpEmptyViewPod = view.findViewById(R.id.vp_empty_view_pod);

        rvNowOnCinema = view.findViewById(R.id.rv_now_on_cinema);

        rvNowOnCinema.setmEmptyView(vpEmptyViewPod);

        rvNowOnCinema.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mNowOnCinemaAdapter = new NowOnCinemaAdapter(getContext(), mCinemaItemDelegate);
        rvNowOnCinema.setAdapter(mNowOnCinemaAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {
                PopularMoviesModel.getInstance().loadMoreMovies(getContext());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PopularMoviesModel.getInstance().forceRefreshMovies(getContext());
            }
        });

        rvNowOnCinema.addOnScrollListener(mSmartScrollListener);

        return view;

    }

    @Override
    public void onTapMovieOverviewButton() {
//        Intent intent = MoviesDetailsActivity.newIntent(getContext());
//        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPopularMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
        mNowOnCinemaAdapter.appendNewData(event.getLoadedPopularMovies());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvNowOnCinema, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
    }

}
