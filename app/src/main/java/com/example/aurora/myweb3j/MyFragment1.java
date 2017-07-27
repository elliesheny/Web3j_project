package com.example.aurora.myweb3j;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;

import java.util.concurrent.ExecutionException;


public class MyFragment1 extends Fragment {

    public MyFragment1() {
    }


    private int[] imageIds = new int[]
            { R.drawable.tab_menu_better , R.drawable.tab_menu_channel
                    , R.drawable.tab_menu_message};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        Button button = (Button) view.findViewById(R.id.search_address);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText editText = (EditText) getView().findViewById(R.id.find_parking);
                int parking_no = Integer.parseInt(editText.getText().toString());
                Utf8String result = null;
                try {
                    result = MainActivity.contract.queryParking(new Uint256(parking_no)).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println("Available hour: " +result.getValue());
                String name = "amanda";
                String phone = "07528976382";
                String parking_add = "John Adams Hall";
                String avail_hour = result+"";
                String hour_1 = avail_hour.substring(0, 23);
                String hour_2 = avail_hour.substring(24, 47);
                String hour_3 = avail_hour.substring(48, 71);
                seller find_seller=new seller();
                find_seller.setseller(parking_no,name,phone, parking_add, hour_1,hour_2,hour_3);
                Intent intent = new Intent(getActivity().getApplicationContext(), NewOrderActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("seller", find_seller);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


        return view;
    }




}
