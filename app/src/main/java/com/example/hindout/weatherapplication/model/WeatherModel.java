package com.example.hindout.weatherapplication.model;

import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hindout on 2018/11/21.
 */

public class WeatherModel{
    @SerializedName("date")
    private String Date;

    private String location;

    @SerializedName("tmp_max")
    private int tmp_max;

    private int tmp;

    @SerializedName("tmp_min")
    private int tmp_min;

    private String cond_txt_d;

    private String cond_txt_n;

    private String cond_txt;

    @SerializedName("pres")
    private String pressure;

    private String hum;

    @SerializedName("wind_spd")
    private String wind_speed;

    private String detail_date;

    private String cond_code_d;

    private int resId;

    private String cond_code;

    @Override
    public String toString() {
        return getWind_speed()+getCond_txt_d()+getDate();
    }

    public int getTmp() {
        return tmp;
    }

    public void setTmp(int tmp) {
        this.tmp = tmp;
    }

    public String getCond_code() {
        return cond_code;
    }

    public void setCond_code(String cond_code) {
        this.cond_code = cond_code;
    }

    public String getCond_code_d() {
        return cond_code_d;
    }

    public String getCond_txt() {
        return cond_txt;
    }

    public void setCond_txt(String cond_txt) {
        this.cond_txt = cond_txt;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public void setCond_code_d(String cond_code_d) {
        this.cond_code_d = cond_code_d;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getTmp_max() {
        return tmp_max;
    }

    public void setTmp_max(int temp_max) {
        this.tmp_max = temp_max;
    }

    public int getTmp_min() {
        return tmp_min;
    }

    public void setTmp_min(int temp_min) {
        this.tmp_min = temp_min;
    }

    public String getCond_txt_d() {
        return cond_txt_d;
    }

    public void setCond_txt_d(String cond_txt_d) {
        this.cond_txt_d = cond_txt_d;
    }

    public String getCond_txt_n() {
        return cond_txt_n;
    }

    public void setCond_txt_n(String cond_txt_n) {
        this.cond_txt_n = cond_txt_n;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getDetail_date() {
        return detail_date;
    }

    public void setDetail_date(String detail_date) {
        this.detail_date = detail_date;
    }
}
