package com.example.hindout.weatherapplication;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.example.hindout.weatherapplication.model.WeatherModel;

import java.util.ArrayList;

/**
 * Created by hindout on 2018/11/21.
 */

public class WeatherContent extends AppCompatActivity {

    public static final String WEATHER_ITEM_ID = "WEATHER_ITEM_ID";
    private ArrayList<WeatherModel> weatherList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_content);
        ViewPager viewPager = (ViewPager) findViewById(R.id.weather_content_viewpager);
        viewPager.setId(R.id.viewPager);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.content_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("今日天气");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        weatherList = WeatherRepository.get().getWeatherList();

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return WeatherContentFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return weatherList.size();
            }

        });

        viewPager.setCurrentItem(getIntent()
        .getIntExtra(WEATHER_ITEM_ID,0));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.contentmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
