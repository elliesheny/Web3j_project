package com.example.aurora.myweb3j;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MyFragment3 extends android.support.v4.app.Fragment {
    public MyFragment3() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_myaccount,container,false);
//        EditText edit_userid = (EditText) view.findViewById(R.id.edit_userid);
//        edit_userid.setText(MainedActivity.user_me.userid+"");
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
//        final EditText edit_userbalance = (EditText) view.findViewById(R.id.edit_userbalance);
//        edit_userbalance.setText(MainedActivity.user_me.balance+"");
//        edit_userbalance.setEnabled(false);
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


//                EditText edit_userid = (EditText) view.findViewById(R.id.edit_userid);
//                MainedActivity.user_me.userid = Integer.parseInt(edit_userid.getText().toString());
//                edit_userid.setText(MainedActivity.user_me.userid+"");
//
//                EditText edit_userpwd = (EditText) view.findViewById(R.id.edit_userpwd);
//                MainedActivity.user_me.password = edit_userpwd.getText().toString();
//                edit_userpwd.setText(MainedActivity.user_me.password);
//
//                EditText edit_username = (EditText) view.findViewById(R.id.edit_username);
//                MainedActivity.user_me.username = edit_username.getText().toString();
//                edit_username.setText(MainedActivity.user_me.username);
//
//                EditText edit_usercar = (EditText) view.findViewById(R.id.edit_usercarno);
//                MainedActivity.user_me.carnumber = edit_usercar.getText().toString();
//                edit_usercar.setText(MainedActivity.user_me.carnumber);
//
//                EditText edit_useremail = (EditText) view.findViewById(R.id.edit_useremail);
//                MainedActivity.user_me.email = edit_useremail.getText().toString();
//                edit_useremail.setText(MainedActivity.user_me.email);
//
//                EditText edit_userphone = (EditText) view.findViewById(R.id.edit_userphone);
//                MainedActivity.user_me.mobilenumber = edit_userphone.getText().toString();
//                edit_userphone.setText(MainedActivity.user_me.mobilenumber+"");
//
//                EditText edit_userbalance = (EditText) view.findViewById(R.id.edit_userbalance);
//                MainedActivity.user_me.balance = Double.valueOf(edit_userbalance.getText().toString());
//                edit_userbalance.setText(MainedActivity.user_me.balance+"");
//
//
//
//
//                new Thread() {
//
//                    public void run() {
//                        try {
//                            Log.e("debug", "debug 2");
//                            final String respond = MainedActivity.acceptServer("usup*"+MainedActivity.user_me.user_coding());
//                            //Toast.makeText(getActivity().getApplicationContext(), seller_string, Toast.LENGTH_LONG).show();
//                            //((MainedActivity)getActivity()).acceptServer("loca*123*234");
//                            Log.e("Return", respond);
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    MainedActivity.user_me.user_decoding(respond);
//                                    EditText edit_userbalance = (EditText) view.findViewById(R.id.edit_userbalance);
//                                    edit_userbalance.setText(MainedActivity.user_me.balance+"");
//
//
//                                }
//                            });
//
//
//
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
//
            }
        });

        Button button_charge = (Button) view.findViewById(R.id.button_charge);
        button_charge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


//                EditText edit_usercharge = (EditText) view.findViewById(R.id.edit_usercharge);
//                MainedActivity.user_me.balance = Double.valueOf(edit_usercharge.getText().toString());
//
//                new Thread() {
//
//                    public void run() {
//                        try {
//
//                            String respond = MainedActivity.acceptServer("usch*"+MainedActivity.user_me.user_coding());
//                            MainedActivity.user_me.user_decoding(respond);
//                            Log.e("Return", respond);
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    EditText edit_userbalance = (EditText) view.findViewById(R.id.edit_userbalance);
//                                    edit_userbalance.setText(MainedActivity.user_me.balance+"");
//
////stuff that updates ui
//
//                                }
//                            });
//
//
//
//
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
//
            }
        });
        return view;
    }
}
