package com.example.hindout.weatherapplication;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.youth.picker.entity.PickerData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hindout on 2018/11/23.
 */

public class Setting extends AppCompatActivity{
    public static PickerData pickerData;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setTitle("设置");
        SettingsFragment fragment = new SettingsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.setting_layout,fragment)
                .commit();
        try {
            handleData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void handleData() throws JSONException {
        pickerData = new PickerData();

//        ArrayList<String> provinces = new ArrayList<>();
        Map<String,String[]> citys = new HashMap<>();
        JSONArray jsonArray = new JSONArray(MainWindow.JSON);
        String[] provinces = new String[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            provinces[i]= jsonObject.getString("name");
            JSONArray child = jsonObject.getJSONArray("city");
            String[] s = new String[child.length()];
            for(int j = 0 ; j < child.length(); j++){
                s[j] = child.getJSONObject(j).getString("name");
            }
            citys.put(jsonObject.getString("name"),s);
        }
        pickerData.setFirstDatas(provinces);
        pickerData.setSecondDatas(citys);
    }


}
