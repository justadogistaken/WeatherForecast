package com.example.hindout.weatherapplication;

import android.os.AsyncTask;

import com.example.hindout.weatherapplication.config.Request;
import com.example.hindout.weatherapplication.model.WeatherModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hindout on 2018/11/21.
 */

public class DownloadWeatherForecast extends AsyncTask<Map<String, String>,Void,String> {
    private callBack call;
    public static final int SUCCESS = 200;
    public static final int FAILED = 500;
    @Override
    protected String doInBackground(Map<String, String>[] maps) {
        try {
            return new String(Request.Get("https://free-api.heweather.com/s6/weather/forecast",maps[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface callBack{
        void updateArray(String result) throws Exception;
    }

    public void setCall(callBack caller){
        call = caller;
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        try {
            call.updateArray(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

