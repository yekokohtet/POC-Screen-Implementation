package com.padcmyanmar.poc_screen_implementation.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.data.vo.PopularMoviesVO;
import com.padcmyanmar.poc_screen_implementation.delegates.CinemaItemDelegate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yekokohtet on 11/7/17.
 */

public class NowOnCinemaFragmentViewHolder extends BaseViewHolder<PopularMoviesVO> {

    private CinemaItemDelegate mCinemaItemDelegate;

    private PopularMoviesVO mData;

    @BindView(R.id.tv_cinema_name)
    TextView tvCinemaName;

    @BindView(R.id.tv_cinema_rating)
    TextView tvCinemaRating;

    @BindView(R.id.tv_cinema_genre)
    TextView tvCinemaGenre;

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;

    @BindView(R.id.iv_cinema_poster)
    ImageView ivCinemaPoster;

    public NowOnCinemaFragmentViewHolder(View itemView, CinemaItemDelegate cinemaItemDelegate) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mCinemaItemDelegate = cinemaItemDelegate;
    }


    @Override
    public void setData(PopularMoviesVO data) {

        mData = data;

        tvCinemaName.setText(data.getTitle());
        tvCinemaRating.setText(String.valueOf(data.getVoteAverage()));
        float rate = data.getPopularity()/200;
        ratingBar.setRating(rate);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.img_placeholder_poster)
                .centerCrop();

        Glide
                .with(itemView.getContext())
                .load("https://image.tmdb.org/t/p/original" + data.getPosterPath())
                .apply(requestOptions)
                .into(ivCinemaPoster);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_movie_overview:
                mCinemaItemDelegate.onTapMovieOverviewButton(mData);
                break;
            case R.id.btn_full_screen:
                mCinemaItemDelegate.onTapImageFullScreenButton();
                break;
        }

    }
}
