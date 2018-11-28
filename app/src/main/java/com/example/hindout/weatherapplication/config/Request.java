package com.example.hindout.weatherapplication.config;

import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by hindout on 2018/11/21.
 * 此类用来作网络请求
 */

public class Request {

    /**
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */

    public static byte[] Get(String url, Map<String, String> params)
            throws Exception{

        /**
         * finUrl 最终UR
         */
        params.put("key","e2ac4a73247349b1956710b971f98c16");
        Log.i("map",params.toString());
        String construUrl;
        Uri uri = Uri.parse(url);
        Iterator<Map.Entry<String,String>> entryIterator = params.entrySet().iterator();

        while(entryIterator.hasNext()){
            Map.Entry<String,String> entry = entryIterator.next();
            uri = uri.buildUpon().appendQueryParameter(entry.getKey(),entry.getValue()).build();
        }
        URL finUrl = new URL(uri.toString());
        HttpURLConnection connection = (HttpURLConnection) finUrl.openConnection();
        Log.i("finalUrl",finUrl.toString());
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = connection.getInputStream();

            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }

            int readLength = 0;
            byte[] buffer = new byte[1024];
            while((readLength = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, readLength);
            }
            outputStream.close();
            return outputStream.toByteArray();
        }
        catch (Exception e){
            return "exception".getBytes();
        }
        finally {
            connection.disconnect();
        }
    }
}
