package com.example.aurora.myweb3j;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static com.example.aurora.myweb3j.MainActivity.contract;

//manage parking lot for the seller
public class ManageFragment extends android.support.v4.app.Fragment {

    public ManageFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage,container,false);
        Button button = (Button) view.findViewById(R.id.button_new_parking);
        //create or modify the data
        button.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final Intent intent_newparking = new Intent(getActivity().getApplicationContext(), NewParkingActivity.class);
                startActivity(intent_newparking);
            }
        });
        //select the occupied hours
        Button button_manage = (Button) view.findViewById(R.id.button_manage_parking);
        button_manage.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                final Intent intent_newparking = new Intent(getActivity().getApplicationContext(), ManageParkingActivity.class);
                startActivity(intent_newparking);
            }
        });


        return view;
    }





}
