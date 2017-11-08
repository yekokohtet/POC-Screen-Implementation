package com.padcmyanmar.poc_screen_implementation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.padcmyanmar.poc_screen_implementation.fragments.MostPopularFragment;
import com.padcmyanmar.poc_screen_implementation.fragments.NowOnCinemaFragment;
import com.padcmyanmar.poc_screen_implementation.fragments.UpcomingFragment;

/**
 * Created by yekokohtet on 11/7/17.
 */

public class CinemaFragmentPagerAdapter extends FragmentPagerAdapter {

    public CinemaFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NowOnCinemaFragment();
            case 1:
                return new UpcomingFragment();
            case 2:
                return new MostPopularFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Now On Cinema";
            case 1:
                return "Upcoming";
            case 2:
                return "Most Popular";
            default:
                return null;
        }
    }
}
