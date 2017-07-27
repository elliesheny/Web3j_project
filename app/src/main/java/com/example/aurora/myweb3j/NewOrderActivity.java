package com.example.aurora.myweb3j;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private seller seller_select= new seller();
    private Spinner spin_day;
    private Context mContext;
    private ArrayList<Day> mData = null;
    private BaseAdapter myAdadpter = null;
    private CheckBox[] check_hour = new CheckBox[24];
    private int[] checkbox_id = new int[24];
    private Button btn_book;
    private String hour_selected = "";
    private String day_selected = "0";
    private String book_send;
    private byte[] hour_buffer = new byte[24];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        seller_select=(seller)intent.getSerializableExtra("seller");
        toolbar.setTitle(seller_select.parking_add);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        mData = new ArrayList<Day>();
        bindViews();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    private void bindViews() {
        spin_day = (Spinner) findViewById(R.id.spin_day);
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        Calendar today = Calendar.getInstance();
        String date_1 = df.format(today.getTime());
        today.add(today.DATE,1);
        String date_2 = df.format(today.getTime());
        today.add(today.DATE,1);
        String date_3 = df.format(today.getTime());
        mData.add(new Day(R.drawable.day,date_1));
        mData.add(new Day(R.drawable.day,date_2));
        mData.add(new Day(R.drawable.day,date_3));

        myAdadpter = new MyAdapter<Day>(mData,R.layout.item_spin_day) {
            @Override
            public void bindView(ViewHolder holder, Day obj) {
                holder.setImageResource(R.id.img_icon,obj.gethIcon());
                holder.setText(R.id.txt_day, obj.gethDay());
            }
        };
        spin_day.setAdapter(myAdadpter);
        spin_day.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
