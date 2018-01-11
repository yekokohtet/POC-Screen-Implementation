package com.padcmyanmar.poc_screen_implementation.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.activities.MoviesDetailsActivity;
import com.padcmyanmar.poc_screen_implementation.adapters.NowOnCinemaAdapter;
import com.padcmyanmar.poc_screen_implementation.components.EmptyViewPod;
import com.padcmyanmar.poc_screen_implementation.components.SmartRecyclerView;
import com.padcmyanmar.poc_screen_implementation.components.SmartScrollListener;
import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;
import com.padcmyanmar.poc_screen_implementation.events.RestApiEvents;
import com.padcmyanmar.poc_screen_implementation.mvp.presenters.NowOnCinemaPresenter;
import com.padcmyanmar.poc_screen_implementation.mvp.views.NowOnCinemaView;
import com.padcmyanmar.poc_screen_implementation.persistence.MoviesContract;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowOnCinemaFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>, NowOnCinemaView {

    private NowOnCinemaPresenter mPresenter;

    private static final int MOVIES_LIST_LOADER_ID = 1001;

    SmartRecyclerView rvNowOnCinema;
    EmptyViewPod vpEmptyViewPod;

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private NowOnCinemaAdapter mNowOnCinemaAdapter;

    private SmartScrollListener mSmartScrollListener;

    public NowOnCinemaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mPresenter = new NowOnCinemaPresenter(getContext(), this);
        mPresenter.onCreateView();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_on_cinema, container, false);

        ButterKnife.bind(this, view);

        vpEmptyViewPod = view.findViewById(R.id.vp_empty_view_pod);

        rvNowOnCinema = view.findViewById(R.id.rv_now_on_cinema);

        rvNowOnCinema.setmEmptyView(vpEmptyViewPod);

        rvNowOnCinema.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        mNowOnCinemaAdapter = new NowOnCinemaAdapter(getContext(), mPresenter);
        rvNowOnCinema.setAdapter(mNowOnCinemaAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {

                Snackbar.make(rvNowOnCinema, "Loading new data.", Snackbar.LENGTH_LONG).show();
                swipeRefreshLayout.setRefreshing(true);

//                PopularMoviesModel.getInstance().loadMoreMovies(getContext());
                mPresenter.onMoviesListEndReach(getContext());
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                PopularMoviesModel.getInstance().forceRefreshMovies(getContext());
                mPresenter.onForceRefresh(getContext());
            }
        });

        rvNowOnCinema.addOnScrollListener(mSmartScrollListener);

        getLoaderManager().initLoader(MOVIES_LIST_LOADER_ID, null, this);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        mPresenter.onStart();

//        List<PopularMoviesVO> moviesList = PopularMoviesModel.getInstance().getMovies();
//        if (!moviesList.isEmpty()) {
//            mNowOnCinemaAdapter.setNewData(moviesList);
//        } else {
//            swipeRefreshLayout.setRefreshing(true);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);

        mPresenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onDestroyView();
    }

//    @Override
//    public void onTapMovieOverviewButton(PopularMoviesVO movies) {
//        Intent intent = MoviesDetailsActivity.newIntent(getContext(), movies.getId()+"");
//        startActivity(intent);
////        Toast.makeText(getContext(), movies.getId()+ " Movie Id", Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onTapImageFullScreenButton() {
//        Toast.makeText(getContext(), "Hey, you clicked the full screen image button!", Toast.LENGTH_SHORT).show();
//    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPopularMoviesDataLoaded(RestApiEvents.MoviesDataLoadedEvent event) {
//        mNowOnCinemaAdapter.appendNewData(event.getLoadedPopularMovies());
//        swipeRefreshLayout.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokingAPI(RestApiEvents.ErrorInvokingAPIEvent event) {
        Snackbar.make(rvNowOnCinema, event.getErrorMsg(), Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getContext(),
                MoviesContract.PopularMovieEntry.CONTENT_URI,
                null,
                null, null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        if (data != null && data.moveToFirst()) {
//            List<PopularMoviesVO> moviesList = new ArrayList<>();
//
//            do {
//                PopularMoviesVO movie = PopularMoviesVO.parseFromCursor(data, getContext());
//                moviesList.add(movie);
//            } while (data.moveToNext());
//
//            mNowOnCinemaAdapter.setNewData(moviesList);
//            swipeRefreshLayout.setRefreshing(false);
//        }
        mPresenter.onDataLoaded(getContext(), data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void displayMoviesList(List<PopularMoviesVO> moviesList) {
        mNowOnCinemaAdapter.setNewData(moviesList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void navigateToMovieOverview(PopularMoviesVO movies) {
        Intent intent = MoviesDetailsActivity.newIntent(getContext(), movies.getId()+"");
        startActivity(intent);
    }

    @Override
    public void navigateToFullPoster() {
        Toast.makeText(getContext(), "Hey, you clicked the full screen image button!", Toast.LENGTH_SHORT).show();
    }

}
