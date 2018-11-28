package com.example.hindout.weatherapplication;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.hindout.weatherapplication.model.WeatherModel;
import java.util.ArrayList;

/**
 * Created by hindout on 2018/11/26.
 */

public class DBHelper extends SQLiteOpenHelper {
    private final static int version = 1;
    private final static String dbName = "weatherdata";
    public DBHelper(Context context){
        super(context,dbName,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createWords = "create table weather("
                +"date text primary key,"
                +"location text,"
                +"tmp_max integer,"
                +"tmp_min integer,"
                +"cond_txt_d text,"
                +"cond_txt_n text,"
                +"pressure text,"
                +"hum text,"
                +"wind_speed text,"
                +"detail_data text,"
                +"cond_code_d text,"
                +"resId integer)";
        db.execSQL(createWords);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists weather");
        onCreate(db);
    }

    public static void insertData(WeatherModel weatherModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put("location",weatherModel.getLocation());
        contentValues.put("tmp_max",weatherModel.getTmp_max());
        contentValues.put("tmp_min",weatherModel.getTmp_min());
        contentValues.put("date",weatherModel.getDate());
        contentValues.put("cond_txt_d",weatherModel.getCond_txt_d());
        contentValues.put("cond_txt_n",weatherModel.getCond_txt_n());
        contentValues.put("pressure",weatherModel.getPressure());
        contentValues.put("hum",weatherModel.getHum());
        contentValues.put("wind_speed",weatherModel.getWind_speed());
        contentValues.put("detail_data",weatherModel.getDetail_date());
        contentValues.put("cond_code_d",weatherModel.getCond_code_d());
        contentValues.put("resId",weatherModel.getResId());
        MainWindow.database.insert("weather",null,contentValues);
    }

    public static ArrayList<WeatherModel> readData(){
        Cursor cursor = MainWindow.database.query("weather",null,null,null,null,null,null,null);
        ArrayList<WeatherModel> list = new ArrayList<>();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            for(int i = 0; i < cursor.getCount(); i++){
                Log.i("searching","searching");
                WeatherModel model = new WeatherModel();
                model.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                model.setTmp_max(cursor.getInt(cursor.getColumnIndex("tmp_max")));
                model.setTmp_min(cursor.getInt(cursor.getColumnIndex("tmp_min")));
                model.setDate(cursor.getString(cursor.getColumnIndex("date")));
                model.setCond_txt_d(cursor.getString(cursor.getColumnIndex("cond_txt_d")));
                model.setCond_txt_n(cursor.getString(cursor.getColumnIndex("cond_txt_n")));
                model.setPressure(cursor.getString(cursor.getColumnIndex("pressure")));
                model.setHum(cursor.getString(cursor.getColumnIndex("hum")));
                model.setWind_speed(cursor.getString(cursor.getColumnIndex("wind_speed")));
                model.setDetail_date(cursor.getString(cursor.getColumnIndex("detail_data")));
                model.setCond_code_d(cursor.getString(cursor.getColumnIndex("cond_code_d")));
                model.setResId(cursor.getInt(cursor.getColumnIndex("resId")));
                list.add(model);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return list;
    }
}
