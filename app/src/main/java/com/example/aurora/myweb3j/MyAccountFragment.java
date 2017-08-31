package com.example.aurora.myweb3j;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aurora.myweb3j.util.Alice;
import com.example.aurora.myweb3j.util.Web3jUtils;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static com.example.aurora.myweb3j.RegisterActivity.ADDRESS;

//my account tab
public class MyAccountFragment extends android.support.v4.app.Fragment {
    public MyAccountFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_my_account,container,false);
        EditText edit_useradd = (EditText) view.findViewById(R.id.edit_useradd);
        edit_useradd.setText(ADDRESS+"");
        Log.d("Address: ",ADDRESS);
        edit_useradd.setEnabled(false);


        //get the inforamtion of user's account
        Utf8String result_buyer = null;
        try {
            result_buyer = MainActivity.contract.queryBuyer().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Buyer: ", result_buyer+"");
        //decode the buyer's information and save them
        String string_buyer = result_buyer+"";
        String buyer_name = (string_buyer.substring(0, string_buyer.indexOf('*')));
        string_buyer=string_buyer.substring(string_buyer.indexOf('*')+1);
        String buyer_phone = (string_buyer.substring(0, string_buyer.indexOf('*')));
        EditText edit_username = (EditText) view.findViewById(R.id.edit_username);
        edit_username.setText(buyer_name);
        EditText edit_userphone = (EditText) view.findViewById(R.id.edit_userphone);
        edit_userphone.setText(buyer_phone);
//
//
        //get balance
        BigDecimal balance_in_ether = null;

        try {
            balance_in_ether = getBalanceEther(LoginActivity.web3j, ADDRESS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d("Balance:", balance_in_ether+"");

        //display balance
        TextView edit_userbalance = (TextView) view.findViewById(R.id.text_userbalance);
        edit_userbalance.setText(balance_in_ether+"");

        //update the information
        Button button_update = (Button) view.findViewById(R.id.button_update);
        button_update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                BigDecimal balance_in_ether = null;

                //update the balance
                try {
                    balance_in_ether = getBalanceEther(LoginActivity.web3j, ADDRESS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.d("Balance:", balance_in_ether+"");
                TextView edit_userbalance = (TextView) view.findViewById(R.id.text_userbalance);
                edit_userbalance.setText(balance_in_ether+"");
                EditText edit_username = (EditText) view.findViewById(R.id.edit_username);
                String buyer_name = edit_username.getText().toString();

                EditText edit_userphone = (EditText) view.findViewById(R.id.edit_userphone);
                String buyer_phone = edit_userphone.getText().toString();
                //send the request to the contract to update user information
                TransactionReceipt transactionReceipt = null;
                try {
                    transactionReceipt = MainActivity.contract.newBuyer(new Utf8String(buyer_name),new Utf8String(buyer_phone)).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                //when getting a receipt, notice the user
                if(!transactionReceipt.getTransactionHash().isEmpty()){


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(), "Updated!", Toast.LENGTH_LONG).show();
                        }});
                }

            }
        });

        //charge some ether on user's account
        Button button_charge = (Button) view.findViewById(R.id.button_charge);
        button_charge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BigInteger amountWei = new BigInteger("500000000000000000");
                try {
                    MainActivity.transferWei(Web3jUtils.getCoinbase(), ADDRESS, amountWei);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }

    //get balance in ether uint
    public static BigDecimal getBalanceEther(Web3j web3j, String address) throws InterruptedException, ExecutionException {
        return weiToEther(Web3jUtils.getBalanceWei(web3j, address));
    }
    //transfer wei to ether
    public static BigDecimal weiToEther(BigInteger wei) {
        return Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
    }


}
