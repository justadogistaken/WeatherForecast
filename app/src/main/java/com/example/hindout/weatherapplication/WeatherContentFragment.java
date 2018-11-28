package com.example.hindout.weatherapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.example.hindout.weatherapplication.model.WeatherModel;

import java.util.ArrayList;

/**
 * Created by hindout on 2018/11/22.
 */

public class WeatherContentFragment extends Fragment {
    private WeatherModel weatherModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_content_fragment, container,false);
        ArrayList<WeatherModel> weatherList = WeatherRepository.get().getWeatherList();
        setLayout(view, weatherList);
        setHasOptionsMenu(true);
        return view;
    }

    private void setLayout(View v, ArrayList<WeatherModel> weatherList){
        int pos = getArguments().getInt(WeatherContent.WEATHER_ITEM_ID);
        Log.i("position",pos+"");
        weatherModel = weatherList.get(pos);
        TextView maxtmp = v.findViewById(R.id.content_max_tmp);
        maxtmp.setText(String.valueOf(weatherModel.getTmp_max())+"°");
        TextView mintmp = v.findViewById(R.id.content_min_tmp);
        mintmp.setText(String.valueOf(weatherModel.getTmp_min())+"°");
        TextView hum = v.findViewById(R.id.content_humidity);
        hum.setText(weatherModel.getHum()+" %");
        TextView pres = v.findViewById(R.id.content_pressure);
        pres.setText(weatherModel.getPressure()+" hPa");
        TextView wind = v.findViewById(R.id.content_wind);
        wind.setText(weatherModel.getWind_speed()+" km/h SE");
        TextView date = v.findViewById(R.id.content_date);
        date.setText(weatherModel.getDetail_date());
        ImageView imageView = v.findViewById(R.id.content_icon);
        imageView.setImageResource(weatherModel.getResId());
        TextView status = v.findViewById(R.id.content_status);
        status.setText(weatherModel.getCond_txt_d());
    }

    /**
     *
     * @param pos
     * @return pos位置上的项目的Fragment，在onCreate取用bundle
     */
    public static Fragment newInstance(int pos){
        Bundle bundle = new Bundle();
        WeatherContentFragment weatherContentFragment = new WeatherContentFragment();
        bundle.putInt(WeatherContent.WEATHER_ITEM_ID, pos);
        weatherContentFragment.setArguments(bundle);
        return weatherContentFragment;
    }

    private void shareWeatherInfo(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String message = "今日白日天气情况："+weatherModel.getCond_txt_d()
                +", 今日夜间天气情况："+weatherModel.getCond_txt_n()
                +", 今日最高温："+weatherModel.getTmp_max()
                +", 今日最低温："+weatherModel.getTmp_min();
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(
                Intent.createChooser(intent, "分享到")
        );
    }
    Intent intent = new Intent(Intent.ACTION_SEND);
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        intent.setType("text/plain");
        String message = "今日白日天气情况："+weatherModel.getCond_txt_d()
                +", 今日夜间天气情况："+weatherModel.getCond_txt_n()
                +", 今日最高温："+weatherModel.getTmp_max()
                +", 今日最低温："+weatherModel.getTmp_min();
        intent.putExtra(Intent.EXTRA_TEXT, message);

//        MenuItem menuItem = menu.findItem(R.id.toolbar_sharing);
//        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i("分享","到");
//                getActivity().startActivity(
//                        Intent.createChooser(intent, "分享到")
//                );
//            }
//        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_sharing:
                startActivity(Intent.createChooser(intent, "分享到"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
