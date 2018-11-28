package com.example.hindout.weatherapplication;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hindout.weatherapplication.model.WeatherModel;
import com.youth.picker.PickerView;
import com.youth.picker.entity.PickerData;
import com.youth.picker.listener.OnPickerClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by hindout on 2018/11/23.
 */

public class SettingsFragment extends Fragment {
    private PickerView pickerView;
    private static Intent intent;
    private NotificationOperation notificationOperation;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.settings_item, container, false);
        notificationOperation = new NotificationOperation(getActivity());
        CardView cardView = (CardView) view.findViewById(R.id.choose_area);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerView.show(v);
            }
        });
        pickerView = new PickerView(getActivity(), Setting.pickerData);
        pickerView.setOnPickerClickListener(new OnPickerClickListener() {
            @Override
            public void OnPickerClick(PickerData pickerData) {

            }

            @Override
            public void OnPickerConfirmClick(PickerData pickerData) {
                Map<String,String> map = new HashMap<>();
                map.put("location",pickerData.getSecondText());
                changeText(R.id.setting_location,pickerData.getSecondText());
                WeatherRepository.Location = pickerData.getSecondText();
                WeatherRepository.get().startDownloadTask(map);
            }
        });

        TextView location = (TextView) view.findViewById(R.id.setting_location);
        location.setText(WeatherRepository.Location);
        CardView notify = (CardView) view.findViewById(R.id.notification);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newStr = WeatherRepository.NOTIFICATION.equals("开") ? "关":"开";
                WeatherRepository.NOTIFICATION = newStr;
                changeText(R.id.setting_notify, newStr);
                if (WeatherRepository.NOTIFICATION.equals("开")){
                    intent = new Intent(getActivity(), NotifyService.class);
                    getActivity().startService(intent);
                }else{
                    getActivity().stopService(intent);
                }
            }
        });
        TextView notifyView = (TextView) view.findViewById(R.id.setting_notify);
        notifyView.setText(WeatherRepository.NOTIFICATION);
        return view;
    }

    /**
     * 处理一些会变化的界面事务
     */

    @Override
    public void onStart() {
        super.onStart();
    }

    private void changeText(int id, String newStr){
        TextView textView = (TextView) getActivity().findViewById(id);
        textView.setText(newStr);
    }
}
