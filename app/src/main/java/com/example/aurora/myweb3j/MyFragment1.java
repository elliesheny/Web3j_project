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
import org.web3j.protocol.core.methods.response.TransactionReceipt;

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
                TransactionReceipt result_receipt = null;
                Utf8String result = null;
                try {
                    result_receipt = MainActivity.contract.queryParking(new Uint256(parking_no)).get();
                    result = MainActivity.contract.uintto().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println("Parking Info: " +result.getValue());
                String coded_result = result.getValue().substring(0, result.getValue().indexOf('%'));
                seller find_seller=new seller();
                find_seller.id = parking_no;
                String temp=coded_result.substring(0, coded_result.indexOf('*'));
                find_seller.name=temp;
                coded_result=coded_result.substring(coded_result.indexOf('*')+1);

                temp=coded_result.substring(0, coded_result.indexOf('*'));
                find_seller.phone=temp;
                coded_result=coded_result.substring(coded_result.indexOf('*')+1);

                temp=coded_result.substring(0, coded_result.indexOf('*'));
                find_seller.post_code=temp.substring(0,temp.length()-1);;
                coded_result=coded_result.substring(coded_result.indexOf('*')+1);

                temp=coded_result.substring(0, coded_result.indexOf('*'));
                Log.d("hours",temp);
                find_seller.available_date_1=temp.substring(0, 24);
                find_seller.available_date_2=temp.substring(24, 48);
                find_seller.available_date_3=temp.substring(48, 72);
                Log.d("avail_hour",find_seller.available_date_1+"and"+find_seller.available_date_2);
                coded_result=coded_result.substring(coded_result.indexOf('*')+1);
                find_seller.parking_add=coded_result.substring(0, coded_result.indexOf('*'));

                //find_seller.setseller(parking_no,find_seller.name,find_seller.phone,find_seller.post_code,find_seller.parking_add,find_seller.avaliable_date_1,find_seller.avaliable_date_2,find_seller.avaliable_date_3);
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
