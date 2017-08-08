package com.example.aurora.myweb3j;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aurora.myweb3j.util.Alice;
import com.example.aurora.myweb3j.util.Web3jUtils;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;


public class MyFragment3 extends android.support.v4.app.Fragment {
    public MyFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_myaccount,container,false);
        EditText edit_userid = (EditText) view.findViewById(R.id.edit_useradd);
        edit_userid.setText(Alice.ADDRESS+"");
//        edit_userid.setEnabled(false);
//
//        EditText edit_userpwd = (EditText) view.findViewById(R.id.edit_userpwd);
//        edit_userpwd.setText(MainedActivity.user_me.password+"");
//
//        EditText edit_username = (EditText) view.findViewById(R.id.edit_username);
//        edit_username.setText(MainedActivity.user_me.username+"");
//
//        EditText edit_usercar = (EditText) view.findViewById(R.id.edit_usercarno);
//        edit_usercar.setText(MainedActivity.user_me.carnumber+"");
//
//        EditText edit_useremail = (EditText) view.findViewById(R.id.edit_useremail);
//        edit_useremail.setText(MainedActivity.user_me.email+"");
//
        //get balance
        BigDecimal balance_in_ether = null;

        try {
            balance_in_ether = getBalanceEther(LoginActivity.web3j, Alice.ADDRESS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Balance:", balance_in_ether+"");

        TextView edit_userbalance = (TextView) view.findViewById(R.id.text_userbalance);
        edit_userbalance.setText(balance_in_ether+"");
//
//        EditText edit_usercharge = (EditText) view.findViewById(R.id.edit_usercharge);
//        edit_usercharge.setText("0");
//
//        EditText edit_userphone = (EditText) view.findViewById(R.id.edit_userphone);
//        edit_userphone.setText(MainedActivity.user_me.mobilenumber+"");


        Button button_update = (Button) view.findViewById(R.id.button_update);
        button_update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                BigDecimal balance_in_ether = null;

                try {
                    balance_in_ether = getBalanceEther(LoginActivity.web3j, Alice.ADDRESS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.d("Balance:", balance_in_ether+"");
                TextView edit_userbalance = (TextView) view.findViewById(R.id.text_userbalance);
                edit_userbalance.setText(balance_in_ether+"");

            }
        });

        Button button_charge = (Button) view.findViewById(R.id.button_charge);
        button_charge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BigInteger amountWei = new BigInteger("500000000000000000");
                try {
                    MainActivity.transferWei(Web3jUtils.getCoinbase(), Alice.ADDRESS, amountWei);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }
    public static BigDecimal getBalanceEther(Web3j web3j, String address) throws InterruptedException, ExecutionException {
        return weiToEther(Web3jUtils.getBalanceWei(web3j, address));
    }
    public static BigDecimal weiToEther(BigInteger wei) {
        return Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
    }


}
