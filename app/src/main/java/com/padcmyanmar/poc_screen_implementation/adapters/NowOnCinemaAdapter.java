package com.padcmyanmar.poc_screen_implementation.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;
import com.padcmyanmar.poc_screen_implementation.delegates.CinemaItemDelegate;
import com.padcmyanmar.poc_screen_implementation.viewholders.NowOnCinemaFragmentViewHolder;

/**
 * Created by yekokohtet on 11/7/17.
 */

public class NowOnCinemaAdapter extends BaseRecyclerAdapter<NowOnCinemaFragmentViewHolder, PopularMoviesVO>{

    private CinemaItemDelegate mCinemaItemDelegate;

    public NowOnCinemaAdapter(Context context, CinemaItemDelegate cinemaItemDelegate) {
        super(context);
        mCinemaItemDelegate = cinemaItemDelegate;
    }

    @Override
    public NowOnCinemaFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View nowOnCinemaView = mLayoutInflater.inflate(R.layout.view_item_now_on_cinema, parent, false);
        return new NowOnCinemaFragmentViewHolder(nowOnCinemaView, mCinemaItemDelegate);
    }
}
