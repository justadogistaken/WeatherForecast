package com.example.hindout.weatherapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by hindout on 2018/11/26.
 */

/**
 * 用来定时通知
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try{
            NotificationOperation notificationOperation =
                    new NotificationOperation(context.getApplicationContext());
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(context
                    , NotificationOperation.ANDROID_CHANNEL_ID);
            builder.setContentTitle("天气预报")//设置通知栏标题
                                    .setContentText("当前温度为：" + NotifyService.model.getTmp()
                                            + "天气状况为：" + NotifyService.model.getCond_txt())
                                    .setSmallIcon(context.getResources()
                                            .getIdentifier("p" + NotifyService.model.getCond_code()
                                                    , "drawable", context.getPackageName()))
                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    .setOngoing(true);
            notificationOperation.getManager().notify(1, builder.build());
        }catch (Exception e){
            e.printStackTrace();
        }
        Intent i = new Intent(context, NotifyService.class);
        context.startService(i);
    }
}
