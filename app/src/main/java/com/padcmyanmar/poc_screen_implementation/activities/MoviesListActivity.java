package com.padcmyanmar.poc_screen_implementation.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.padcmyanmar.poc_screen_implementation.POCScreenImplApp;
import com.padcmyanmar.poc_screen_implementation.R;
import com.padcmyanmar.poc_screen_implementation.adapters.CinemaFragmentPagerAdapter;
import com.padcmyanmar.poc_screen_implementation.data.models.PopularMoviesModel;

import javax.inject.Inject;

public class MoviesListActivity extends AppCompatActivity {

    @Inject
    PopularMoviesModel mPopularMoviesModel;

    ViewPager vpCinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        POCScreenImplApp mPOCScreeImplApp = (POCScreenImplApp) getApplicationContext();
        mPOCScreeImplApp.getPOCAppComponent().inject(this);

        mPopularMoviesModel.startLoadingPopularMovies(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        vpCinema = findViewById(R.id.vp_cinema);
        PagerAdapter pagerAdapter = new CinemaFragmentPagerAdapter(getSupportFragmentManager());
        vpCinema.setAdapter(pagerAdapter);

        TabLayout tabLayoutCinema = findViewById(R.id.tl_cinema);
        tabLayoutCinema.setupWithViewPager(vpCinema);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_now_on_cinema, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
