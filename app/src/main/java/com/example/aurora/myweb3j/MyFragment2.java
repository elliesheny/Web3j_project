package com.example.aurora.myweb3j;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyFragment2 extends android.support.v4.app.Fragment {

    public MyFragment2() {
    }
    //private order[] orders = new order[10];
    private String order_string;
    private String[] order_strings = new String[10];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order,container,false);

//
//        new Thread() {
//
//            public void run() {
//                try {
//                    order_string = ((MainedActivity)getActivity()).acceptServer("reor*"+MainedActivity.user_me.user_coding());
//                    //Toast.makeText(getActivity().getApplicationContext(), seller_string, Toast.LENGTH_LONG).show();
//                    //((MainedActivity)getActivity()).acceptServer("loca*123*234");
//                    Log.e("Return", order_string);
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            for(int i=0;i<10;i++)
//                            {
//                                orders[i]=new order();
//                            }
//                            String message_type;
//                            int order_number = 0;
//
//                            for(int i = 0; i<10; i++){
//                                orders[i]=new order(0,0);}
//                            message_type=order_string.substring(0, order_string.indexOf('*'));
//                            order_string=order_string.substring(order_string.indexOf('*')+1);
//                            Log.e("Return", "ready");
//                            Log.e("Return", message_type);
//                            if(message_type.equals("reor")) {
//                                Log.e("Return", "order received");
//                                String check=order_string.substring(0,1);
//                                if(check.equals("0"))
//                                {}
//                                else{
//
//                                while(order_string.indexOf('%')>=0){
//
//                                    order_strings[order_number] = order_string.substring(0, order_string.indexOf('%'));
//                                    order_string = order_string.substring(order_string.indexOf('%') + 1);
//                                    orders[order_number].order_decoding(order_strings[order_number]);
//                                    order_number++;
//                                }
//
//                                Log.e("Return", order_string);
//                                List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
//
//                                for (int i = 0; i < order_number; i++)
//                                {
//                                    Map<String, Object> listItem = new HashMap<String, Object>();
//
//                                    listItem.put("order_createtime", orders[i].create_time);
//                                    listItem.put("order_price", orders[i].price);
//                                    listItems.add(listItem);
//                                }
//                                Log.e("Return", "debug 2");
//                                SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity().getApplicationContext(), listItems,
//                                        R.layout.list_order,
//                                        new String[]{"order_createtime", "order_price"},
//                                        new int[]{R.id.order_createtime, R.id.order_price});
//
//                                ListView list = (ListView) getView().findViewById(R.id.list_order);
//                                list.setAdapter(simpleAdapter);
//
//                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                    // 第position项被单击时激发该方法。
//                                    @Override
//                                    public void onItemClick(AdapterView<?> parent, View view,
//                                                            int position, long id) {
//                                        Log.e("Return", "click ok");
//                                        //Toast.makeText(getActivity().getApplicationContext(), seller_address[position] + "被单击了", Toast.LENGTH_LONG).show();
//                                        //jump to NewOrderActivity
//                                        Intent intent = new Intent(getActivity().getApplicationContext(), ViewOrderActivity.class);
////                                      intent.putExtra(MainedActivity.EXTRA_MESSAGE,seller_strings[position]);
////                                      startActivity(intent);
//                                        Bundle bundle=new Bundle();
//                                        bundle.putSerializable("order", orders[position]);
//                                        intent.putExtras(bundle);
//                                        startActivity(intent);
//                                    }
//                                });
//                                list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                    @Override
//                                    public void onItemSelected(AdapterView<?> parent, View view,
//                                                               int position, long id) {
//                                        Log.e("Return", "select ok");
//                                    }
//
//                                    @Override
//                                    public void onNothingSelected(AdapterView<?> parent) {
//                                    }
//                                });
//
//                            }
//
//
//                        }}
//                    });
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
        Button button = (Button) view.findViewById(R.id.button_update_order);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


//                new Thread() {
//
//                    public void run() {
//                        try {
//                            order_string = ((MainedActivity)getActivity()).acceptServer("reor*"+MainedActivity.user_me.user_coding());
//                            //Toast.makeText(getActivity().getApplicationContext(), seller_string, Toast.LENGTH_LONG).show();
//                            //((MainedActivity)getActivity()).acceptServer("loca*123*234");
//                            Log.e("Return", order_string);
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    //stuff that updates ui
//
//                                    for(int i=0;i<10;i++)
//                                    {
//                                        orders[i]=new order();
//                                    }
//                                    String message_type;
//                                    int order_number = 0;
//
//                                    for(int i = 0; i<10; i++){
//                                        orders[i]=new order(0,0);}
//                                    message_type=order_string.substring(0, order_string.indexOf('*'));
//                                    order_string=order_string.substring(order_string.indexOf('*')+1);
//                                    Log.e("Return", "ready");
//                                    Log.e("Return", message_type);
//                                    if(message_type.equals("reor")) {
//                                        Log.e("Return", "order received");
//                                        String check=order_string.substring(0,1);
//                                        if(check.equals("0"))
//                                        {}
//                                        else{
//
//                                            while(order_string.indexOf('%')>=0){
//
//                                                order_strings[order_number] = order_string.substring(0, order_string.indexOf('%'));
//                                                order_string = order_string.substring(order_string.indexOf('%') + 1);
//                                                orders[order_number].order_decoding(order_strings[order_number]);
//                                                order_number++;
//                                            }
//
//                                            Log.e("Return", order_string);
//                                            List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
//
//                                            for (int i = 0; i < order_number; i++)
//                                            {
//                                                Map<String, Object> listItem = new HashMap<String, Object>();
//
//                                                listItem.put("order_createtime", orders[i].create_time);
//                                                listItem.put("order_price", orders[i].price);
//                                                listItems.add(listItem);
//                                            }
//                                            Log.e("Return", "debug 2");
//                                            SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity().getApplicationContext(), listItems,
//                                                    R.layout.list_order,
//                                                    new String[]{"order_createtime", "order_price"},
//                                                    new int[]{R.id.order_createtime, R.id.order_price});
//
//                                            ListView list = (ListView) getView().findViewById(R.id.list_order);
//                                            list.setAdapter(simpleAdapter);
//
//                                            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                                                // 第position项被单击时激发该方法。
//                                                @Override
//                                                public void onItemClick(AdapterView<?> parent, View view,
//                                                                        int position, long id) {
//                                                    Log.e("Return", "click ok");
//
//                                                    Intent intent = new Intent(getActivity().getApplicationContext(), ViewOrderActivity.class);
//                                                    Bundle bundle=new Bundle();
//                                                    bundle.putSerializable("order", orders[position]);
//                                                    intent.putExtras(bundle);
//                                                    startActivity(intent);
//                                                }
//                                            });
//                                            list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                                                @Override
//                                                public void onItemSelected(AdapterView<?> parent, View view,
//                                                                           int position, long id) {
//                                                    Log.e("Return", "select ok");
//                                                }
//
//                                                @Override
//                                                public void onNothingSelected(AdapterView<?> parent) {
//                                                }
//                                            });
//
//                                        }
//
//
//                                    }}
//                            });
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }.start();
            }
        });
        return view;
    }

}
