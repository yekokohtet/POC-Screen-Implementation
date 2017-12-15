package com.padcmyanmar.poc_screen_implementation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.viewholders.MoviesTrailerViewHolder;

/**
 * Created by yekokohtet on 12/12/17.
 */

public class MoviesTrailerAdapter extends RecyclerView.Adapter<MoviesTrailerViewHolder> {

    private LayoutInflater mLayoutInflater;

    public MoviesTrailerAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MoviesTrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.view_item_movies_trialer, parent, false);
        return new MoviesTrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesTrailerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
