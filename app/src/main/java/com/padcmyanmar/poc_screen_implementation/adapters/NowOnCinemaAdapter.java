package com.padcmyanmar.poc_screen_implementation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.viewholders.CinemaFragmentViewHolder;

/**
 * Created by yekokohtet on 11/7/17.
 */

public class NowOnCinemaAdapter extends RecyclerView.Adapter<CinemaFragmentViewHolder> {

    private LayoutInflater mLayoutInflater;

    public NowOnCinemaAdapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public CinemaFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View nowOnCinemaView = mLayoutInflater.inflate(R.layout.view_item_now_on_cinema, parent, false);
        return new CinemaFragmentViewHolder(nowOnCinemaView);
    }

    @Override
    public void onBindViewHolder(CinemaFragmentViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }
}
