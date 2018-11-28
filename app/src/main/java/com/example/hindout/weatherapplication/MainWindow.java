package com.example.hindout.weatherapplication;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.hindout.weatherapplication.model.WeatherModel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.example.hindout.weatherapplication.NotificationOperation.ANDROID_CHANNEL_ID;

public class MainWindow extends AppCompatActivity {

    public static boolean isTablet;
    public static String JSON;
    public static SQLiteDatabase database, readdata;
//    public static NotificationOperation notificationOperation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        DBHelper dbHelper = new DBHelper(this);

        database = dbHelper.getWritableDatabase();
        readdata = dbHelper.getReadableDatabase();
        //获得Json
        try {
            JSON = inputJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isTablet = judgeScreenLayout();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        if(!isTablet){
            //监控toolbar显示
            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if(Math.abs(verticalOffset) > appBarLayout.getTotalScrollRange()/1.2){
                        toolbar.setTitle(WeatherRepository.Location);
                    }else{
                        toolbar.setTitle("");
                    }
                }
            });
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.weather_list,
                new WeatherListFragment())
                .commit();
    }

    /**
     * 在onStart中处理一些可能随着地区改变的部分
     */

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 判断是否为平板
     * @return true为平板，false为手机
     */
    private boolean judgeScreenLayout(){
       return (this.getResources().getConfiguration().screenLayout& Configuration.SCREENLAYOUT_SIZE_MASK)
               >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().toString().equals("Location")){
            Intent intent = new Intent(this, Map_Activity.class);
            startActivity(intent);
        }

        if(item.getTitle().toString().equals("Settings")){
            if(!isTablet){
                Intent intent = new Intent(this, Setting.class);
                startActivity(intent);
            }else{
                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_weather, settingsFragment)
                        .commit();
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private String inputJson() throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(
                getAssets().open("china.json"),"UTF-8"
        );
        BufferedReader bufferedReader = new BufferedReader(
                inputStreamReader
        );
        String line;
        StringBuilder stringBuilder = new StringBuilder();
        while((line = bufferedReader.readLine())!=null){
            stringBuilder.append(line);
        }
        inputStreamReader.close();
        bufferedReader.close();
        return stringBuilder.toString();
    }
}
