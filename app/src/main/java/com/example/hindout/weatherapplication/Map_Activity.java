package com.example.hindout.weatherapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

/**
 * Created by hindout on 2018/11/22.
 */

public class Map_Activity extends AppCompatActivity {
    public LocationClient locationClient;
    private BaiduMap baiduMap;
    private boolean isFirstLocation = true;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //这个判断是为了防止每次定位都重新设置中心点和marker
                if (isFirstLocation) {
                    isFirstLocation = false;
                    //设置并显示中心点
                    setPosition2Center(baiduMap, bdLocation, true);
//                    baiduMap.setMyLocationData();
                }

            }
            public void setPosition2Center(BaiduMap map, BDLocation bdLocation, Boolean isShowLoc) {
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(bdLocation.getRadius())
                        .direction(bdLocation.getRadius()).latitude(bdLocation.getLatitude())
                        .longitude(bdLocation.getLongitude()).build();
                map.setMyLocationData(locData);

                if (isShowLoc) {
                    LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(ll);
                    map.animateMapStatus(mapStatusUpdate);
                }
            }

        });
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
        setContentView(R.layout.baidu_map);

        MapView mapView = (MapView) findViewById(R.id.baidu_map);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        initLocation();
        locationClient.start();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            // 检查该权限是否已经获取
//            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
//
//            // sd卡权限
//            String[] SdCardPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            if (ContextCompat.checkSelfPermission(this, SdCardPermission[0]) != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                ActivityCompat.requestPermissions(this, SdCardPermission, 100);
//            }
//
//            //手机状态权限
//            String[] readPhoneStatePermission = {Manifest.permission.READ_PHONE_STATE};
//            if (ContextCompat.checkSelfPermission(this, readPhoneStatePermission[0]) != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                ActivityCompat.requestPermissions(this, readPhoneStatePermission, 200);
//            }
//
//            //定位权限
//            String[] locationPermission = {Manifest.permission.ACCESS_FINE_LOCATION};
//            if (ContextCompat.checkSelfPermission(this, locationPermission[0]) != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                ActivityCompat.requestPermissions(this, locationPermission, 300);
//            }
//
//            String[] ACCESS_COARSE_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION};
//            if (ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION[0]) != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                ActivityCompat.requestPermissions(this, ACCESS_COARSE_LOCATION, 400);
//            }
//
//
//            String[] READ_EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};
//            if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE[0]) != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                ActivityCompat.requestPermissions(this, READ_EXTERNAL_STORAGE, 500);
//            }
//
//            String[] WRITE_EXTERNAL_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE[0]) != PackageManager.PERMISSION_GRANTED) {
//                // 如果没有授予该权限，就去提示用户请求
//                ActivityCompat.requestPermissions(this, WRITE_EXTERNAL_STORAGE, 600);
//            }
//
//        }else{
//            //doSdCardResult();
//        }


//        BDLocation location = new BDLocation();
//        MyLocationData.Builder builder = new MyLocationData.Builder();
//        builder.latitude(location.getLatitude());
//        builder.longitude(location.getLongitude());
//        MyLocationData locationData = builder.build();



            // 设置定位数据


    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        int span = 5000;
        option.setScanSpan(span);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        locationClient.setLocOption(option);
    }
}
