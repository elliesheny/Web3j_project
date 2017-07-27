package com.example.aurora.myweb3j;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment4 extends android.support.v4.app.Fragment {
    public MyFragment4() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help,container,false);
        //TextView txt_content = (TextView) view.findViewById(R.id.txt_content);
        //txt_content.setText("Forth Fragment");
        Log.e("HEHE", "4");
        return view;
    }
}
