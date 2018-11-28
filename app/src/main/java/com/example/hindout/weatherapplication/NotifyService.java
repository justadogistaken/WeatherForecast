package com.example.hindout.weatherapplication;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.hindout.weatherapplication.config.Request;
import com.example.hindout.weatherapplication.model.WeatherModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hindout on 2018/11/26.
 */

public class NotifyService extends Service{
    public static WeatherModel model;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void request(){
        Map<String,String> map = new HashMap<>();
        map.put("location",WeatherRepository.Location);
        AsyncTask<Map<String,String>,Void,String> asyncTask
                =new AsyncTask<Map<String, String>, Void, String>() {
            @Override
            protected String doInBackground(Map<String, String>[] maps) {
                try {
                    return new String(Request.Get("https://free-api.heweather.com/s6/weather/now",maps[0]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "exception";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                int time = 10000;
                Gson gson = new Gson();
                JSONObject now = new JSONObject();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    jsonObject = jsonObject
                            .getJSONArray("HeWeather6")
                            .getJSONObject(0);
                    now  = jsonObject.getJSONObject("now");
                    now.put("date", jsonObject.getJSONObject("update").getString("loc"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                WeatherModel model = gson.fromJson(now.toString(), WeatherModel.class);
                long triggerAtTime = SystemClock.elapsedRealtime()+time;
                Intent intent1 = new Intent(NotifyService.this, AlarmReceiver.class);
                NotifyService.this.model = model;
                PendingIntent pendingIntent = PendingIntent.getBroadcast(NotifyService.this,0,intent1,0);
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
            }
        };
        asyncTask.execute(map);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        request();
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        int time = 10000;
//        fresh.freshing();
//        long triggerAtTime = SystemClock.elapsedRealtime()+time;
//        Intent intent1 = new Intent(this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent1,0);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

}
