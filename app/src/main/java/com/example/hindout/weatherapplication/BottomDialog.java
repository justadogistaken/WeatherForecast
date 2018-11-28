package com.example.hindout.weatherapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hindout on 2018/11/24.
 */

public class BottomDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog bottomDialog = new Dialog(getActivity(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.choose_area_fragment, null);
        LinearLayout linearLayout = contentView.findViewById(R.id.bottom_layout);
        linearLayout.removeAllViews();
        View view =  LayoutInflater.from(getActivity()).inflate(R.layout.mytextview, linearLayout, false);
        TextView textView = (TextView) view.findViewById(R.id.mytextview);
        textView.setText("长沙");
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("location",((TextView) v).getText().toString());
                map.put("key","e2ac4a73247349b1956710b971f98c16");
                WeatherRepository.get().startDownloadTask(map);
            }
        });
        linearLayout.addView(textView);
        View view1 =  LayoutInflater.from(getActivity()).inflate(R.layout.mytextview, linearLayout, false);
        TextView textView1 = (TextView) view1.findViewById(R.id.mytextview);
        textView1.setText("北京");
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> map = new HashMap<>();
                map.put("location",((TextView) v).getText().toString());
                map.put("key","e2ac4a73247349b1956710b971f98c16");
                WeatherRepository.get().startDownloadTask(map);
                WeatherRepository.Location = ((TextView) v).getText().toString();
                changeText(R.id.setting_location, WeatherRepository.Location);
                getDialog().dismiss();
            }
        });
        linearLayout.addView(textView1);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.setCanceledOnTouchOutside(true);
//        LinearLayout linearLayout = bottomDialog.findViewById(R.id.bottom_layout);
//        linearLayout.removeAllViews();
//
//        linearLayout.addView(textView);
        return bottomDialog;
    }

    private void changeText(int id, String newStr){
        TextView textView = (TextView) getActivity().findViewById(id);
        textView.setText(newStr);
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v = LayoutInflater.from(getActivity()).inflate(R.layout.choose_area_fragment, null);
//        setStyle(R.style.BottomDialog,0);
////        ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
////        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
////        v.setLayoutParams(layoutParams);
//        getActivity().getWindow().setGravity(Gravity.BOTTOM);
//        getActivity().getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
//        return v;
//    }
}
