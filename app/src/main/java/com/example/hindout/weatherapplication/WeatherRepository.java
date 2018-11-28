package com.example.hindout.weatherapplication;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import com.example.hindout.weatherapplication.model.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hindout on 2018/11/21.
 */

public class WeatherRepository {
    public static String NOTIFICATION = "关";
    private static WeatherRepository mWeatherRepository;
    private ArrayList<WeatherModel> weatherModelArrayList;
    private DoneDownload download;
    public static String Location = "长沙";
    private DownloadWeatherForecast downloadWeatherForecast;

    private WeatherRepository(){
        weatherModelArrayList = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("location",Location);
        startDownloadTask(map);
    }

    /**
     * 开启任务，下载JSON
     * @param map 数据参数
     */

    public void startDownloadTask(Map<String, String> map){
        downloadWeatherForecast = new DownloadWeatherForecast();
        downloadWeatherForecast.setCall(new DownloadWeatherForecast.callBack() {
            @Override
            public void updateArray(String result) throws Exception {
                //连接成功的标志
                if(!result.equals("exception")){
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
                    jsonObject = jsonArray.getJSONObject(0);
                    jsonArray = jsonObject.getJSONArray("daily_forecast");
                    Gson gson = new Gson();
                    MainWindow.database.execSQL("DELETE FROM WEATHER");
                    weatherModelArrayList = gson.fromJson(jsonArray.toString(), new TypeToken<List<WeatherModel>>(){}.getType());
                    weatherModelArrayList = specialOperation(weatherModelArrayList);
                    download.getData(weatherModelArrayList);
                }
                else{
//                    weatherModelArrayList = specialOperation(DBHelper.readData());
                    weatherModelArrayList = DBHelper.readData();
                    download.getData(weatherModelArrayList);
                }
            }
        });
        downloadWeatherForecast.execute(map);
    }

    private ArrayList<WeatherModel> specialOperation(ArrayList<WeatherModel> arrayList) throws Exception{
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyy-MM-dd");

        for(int i = 0; i < arrayList.size(); i++){

            WeatherModel model = arrayList.get(i);
            model.setDetail_date(model.getDate());
            model.setLocation(WeatherRepository.Location);
            DBHelper.insertData(model);
            Date date = simpleDateFormat.parse(model.getDate());
            calendar.setTime(date);
            if(i != 0 && i != 1 ){
                model.setDate(
                        DateTable.weekday[calendar.get(Calendar.DAY_OF_WEEK) - 1]
                );
                continue;
            }
            if(i == 0){
                model.setDate("今天 "+DateTable.month[calendar.get(Calendar.MONTH)]
                        +" "+calendar.get(Calendar.DATE));
            }else{
                model.setDate("明天");
            }
        }
        return arrayList;
    }

    public void setDownload(DoneDownload download) {
        this.download = download;
    }

    public static WeatherRepository get(){
        if(mWeatherRepository == null){
            mWeatherRepository = new WeatherRepository();
        }
        return mWeatherRepository;
    }

    public ArrayList<WeatherModel> getWeatherList(){
        return weatherModelArrayList;
    }

    /**
     * 数据下载好后，返回给需要的UI线程
     */

    public interface DoneDownload{
        void getData(ArrayList<WeatherModel> arrayList);
    }
}

class DateTable{
    public static String[] weekday = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
    public static String[] month = {"一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"};

}
