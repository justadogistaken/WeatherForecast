package com.example.hindout.weatherapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.hindout.weatherapplication.model.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hindout on 2018/11/21.
 */

public class WeatherListFragment extends Fragment {
    private ArrayList<WeatherModel> weatherList = new ArrayList<>();
    private WeatherRepository repository;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = WeatherRepository.get();
        //避免退出后再进全空白
        if(repository.getWeatherList().size()>0){
            weatherList.clear();
            weatherList.addAll(repository.getWeatherList());
            updateUI();
        }else{
            repository.setDownload(new WeatherRepository.DoneDownload() {
                @Override
                public void getData(ArrayList<WeatherModel> arrayList) {
                    weatherList.clear();
                    weatherList.addAll(arrayList);
                    updateUI();
                    Log.i("old","old");
                }
            });
        }

        RefreshLayout refreshLayout = getActivity().findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                Map<String, String> map = new HashMap<>();
                map.put("location","长沙");
                repository.setDownload(new WeatherRepository.DoneDownload() {
                    @Override
                    public void getData(ArrayList<WeatherModel> arrayList) {
                        weatherList.clear();
                        weatherList.addAll(arrayList);
                        updateUI();
                        refreshLayout.finishRefresh();
                    }
                });
                repository.startDownloadTask(map);

            }
        });
    }

    private void updateUI(){
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.weather_list);
                linearLayout.removeAllViews();
                for(int i = 0; i< weatherList.size(); i++){
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.weather_list, linearLayout, false);
            view.setOnClickListener(new View.OnClickListener() {
                private int number;

                private View.OnClickListener receiveId(int num){
                    number = num;
                    return this;
                }

                @Override
                public void onClick(View v) {
                    if(!MainWindow.isTablet){
                        Intent intent = new Intent(getActivity(), WeatherContent.class);
                        intent.putExtra(WeatherContent.WEATHER_ITEM_ID,number);
                        startActivity(intent);
                    }else{
                        v.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if(hasFocus){
                                    v.setBackgroundColor(getResources()
                                            .getColor(R.color.colorBackground, null));
                                }
                                else
                                    v.setBackgroundColor(getResources()
                                            .getColor(R.color.colorWhite, null));
                            }
                        });
                        android.support.v4.app.Fragment fragment
                                = WeatherContentFragment
                                .newInstance(number);
                        ((android.support.v4.app.FragmentActivity) getActivity())
                                .getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_weather, fragment)
                                .commit();
                    }
                }
            }.receiveId(i));
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        v.callOnClick();
                    }
                    return false;
                }
            });
            ImageView imageView = (ImageView) view.findViewById(R.id.icon);
            int resId = getResources()
                    .getIdentifier("p"+weatherList.get(i).getCond_code_d()
                            ,"drawable",getActivity().getPackageName());
            imageView.setImageResource(resId);
            weatherList.get(i).setResId(resId);
            TextView dayView = (TextView) view.findViewById(R.id.day_info);
            dayView.setText(weatherList.get(i).getDate());
            TextView statusText = (TextView) view.findViewById(R.id.status);
            statusText.setText(weatherList.get(i).getCond_txt_d());
            TextView maxView = (TextView) view.findViewById(R.id.tmp_max);
            maxView.setText(String.valueOf(weatherList.get(i).getTmp_max()));
            TextView minText = (TextView) view.findViewById(R.id.tmp_min);
            minText.setText(String.valueOf(weatherList.get(i).getTmp_min()));
            linearLayout.addView(view);

            if(!MainWindow.isTablet){
                setMainWindow();
            }
        }
    }

    /**
     * 更新主界面
     */
    private void setMainWindow(){
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        WeatherModel weatherModel = WeatherRepository
                .get()
                .getWeatherList().get(0);
        ImageView imageView = appCompatActivity.findViewById(R.id.weather_status_icon);
        imageView.setImageResource(getResources()
                .getIdentifier("p"+weatherModel.getCond_code_d()
                        ,"drawable",appCompatActivity.getPackageName()));
        TextView degree = (TextView) appCompatActivity.findViewById(R.id.degree_text);
        degree.setText(weatherModel.getTmp_max()+"");
        TextView update = (TextView) appCompatActivity.findViewById(R.id.weather_status);
        update.setText("天气状态:  "+weatherModel.getCond_txt_d());
        TextView hum = (TextView) appCompatActivity.findViewById(R.id.weather_humi);
        hum.setText("相对湿度:  "+weatherModel.getHum());
        TextView pre = (TextView) appCompatActivity.findViewById(R.id.weather_pres);
        pre.setText("大气压强:  "+weatherModel.getPressure());
    }
}
