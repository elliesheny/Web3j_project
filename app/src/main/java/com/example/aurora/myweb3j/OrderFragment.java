package com.example.aurora.myweb3j;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.aurora.myweb3j.util.Order;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.aurora.myweb3j.MainActivity.order_string;
//view order history
public class OrderFragment extends android.support.v4.app.Fragment {

    public OrderFragment() {
    }
    private Order[] Orders = new Order[4];
    private int[] imageIds = new int[]
            { R.drawable.tab_menu_better , R.drawable.tab_menu_channel
                    , R.drawable.tab_menu_message, R.drawable.tab_menu_better};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order,container,false);
        //create the order classes
        for(int i=0;i<4;i++)
        {
            Orders[i]=new Order();
        }
        int order_number = 0;

        Log.d("Return", "");
        //when there is order history
        while(order_string.indexOf('%')>0){

            //decode the order string and save them to the classes
            String temp = order_string.substring(0, order_string.indexOf('%'));
            Log.d("Order "+order_number, temp);

            Orders[order_number].id = Integer.parseInt((temp.substring(0, temp.indexOf('*'))).substring(1));
            temp=temp.substring(temp.indexOf('*')+1);
            Orders[order_number].parking_no = Integer.parseInt((temp.substring(0, temp.indexOf('*'))).substring(1));
            temp=temp.substring(temp.indexOf('*')+1);
            Orders[order_number].state = Integer.parseInt((temp.substring(0, temp.indexOf('*'))).substring(1,2));
            temp=temp.substring(temp.indexOf('*')+1);
            Orders[order_number].price = (Double.parseDouble(temp.substring(0, temp.indexOf('*')).substring(1)))/1000000000000000000d;
            temp=temp.substring(temp.indexOf('*')+1);
            Orders[order_number].date = Long.parseLong(temp.substring(0, temp.indexOf('*')).substring(1));
            temp=temp.substring(temp.indexOf('*')+1);
            Orders[order_number].hour_new = temp;

            Log.d("Order detail: "," id:"+ Orders[order_number].id+" parking no:"+ Orders[order_number].parking_no+" state:"+ Orders[order_number].state+" price:"+ Orders[order_number].price+" date:"+ Orders[order_number].date+" new hour:"+ Orders[order_number].hour_new);

            order_string = order_string.substring(order_string.indexOf('%') + 1);

            order_number++;

        }
        final int order_number1 = order_number;
        //display the orders
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {


                List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

                //set each item
                for (int i = 0; i < order_number1; i++)
                {
                    Map<String, Object> listItem = new HashMap<String, Object>();
                    listItem.put("header", imageIds[i]);
                    listItem.put("order_date", Orders[i].date);
                    listItem.put("order_price", Orders[i].price);
                    listItems.add(listItem);
                }
                //set the adapter
                SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity().getApplicationContext(), listItems,
                R.layout.list_order,
                new String[]{"order_date", "header", "order_price"},
                new int[]{R.id.order_date, R.id .img, R.id.order_price});
                ListView list = (ListView) view.findViewById(R.id.listed_order);
                // set this Adapter to ListView
                list.setAdapter(simpleAdapter);
                //bind a listener to ListView
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    // 第position项被单击时激发该方法。
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Log.e("Return", "click ok");
                        //jump to NewOrderActivity
                        Intent intent = new Intent(getActivity().getApplicationContext(), ViewOrderActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("Order", Orders[position]);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    // when selected
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        Log.e("Return", "select ok");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
                    }
                    });

//
        //update the order history
        Button button = (Button) view.findViewById(R.id.button_update_order);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                TransactionReceipt result_receipt = null;
                Utf8String result_order = null;
                try {
                    result_receipt = MainActivity.contract.listedOrder().get();
                    result_order = MainActivity.contract.uintto().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                System.out.println("Recent Order Hash: " +result_receipt.getTransactionHash());
                System.out.println("Order Result: " +result_order.getValue());
                order_string = result_order.getValue();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for(int i=0;i<4;i++)
                        {
                            Orders[i]=new Order();
                        }
                        int order_number = 0;

                        for(int i = 0; i<4; i++){
                            Orders[i]=new Order();}
                        Log.d("Return", "");
                        while(order_string.indexOf('%')>0){

                            String temp = order_string.substring(0, order_string.indexOf('%'));
                            Log.d("Order "+order_number, temp);

                            Orders[order_number].id = Integer.parseInt((temp.substring(0, temp.indexOf('*'))).substring(1));
                            temp=temp.substring(temp.indexOf('*')+1);
                            Orders[order_number].parking_no = Integer.parseInt((temp.substring(0, temp.indexOf('*'))).substring(1));
                            temp=temp.substring(temp.indexOf('*')+1);
                            Orders[order_number].state = Integer.parseInt((temp.substring(0, temp.indexOf('*'))).substring(1,2));
                            temp=temp.substring(temp.indexOf('*')+1);
                            Orders[order_number].price = (Double.parseDouble(temp.substring(0, temp.indexOf('*')).substring(1)))/1000000000000000000d;
                            temp=temp.substring(temp.indexOf('*')+1);
                            Orders[order_number].date = Long.parseLong(temp.substring(0, temp.indexOf('*')).substring(1));
                            temp=temp.substring(temp.indexOf('*')+1);
                            Orders[order_number].hour_new = temp;

                            Log.d("Order detail: "," id:"+ Orders[order_number].id+" parking no:"+ Orders[order_number].parking_no+" state:"+ Orders[order_number].state+" price:"+ Orders[order_number].price+" date:"+ Orders[order_number].date+" new hour:"+ Orders[order_number].hour_new);

                            order_string = order_string.substring(order_string.indexOf('%') + 1);

                            order_number++;

                        }
                        final int finalOrder_number = order_number;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();

                                for (int i = 0; i < finalOrder_number; i++)
                                {
                                    Map<String, Object> listItem = new HashMap<String, Object>();
                                    listItem.put("header", imageIds[i]);
                                    long timestamp = Long.parseLong(Orders[i].date+"") * 1000L;
                                    listItem.put("order_date", getDate(timestamp));
                                    listItem.put("order_price", Orders[i].price);
                                    listItems.add(listItem);
                                }
                                SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity().getApplicationContext(), listItems,
                                        R.layout.list_order,
                                        new String[]{"order_date", "header", "order_price"},
                                        new int[]{R.id.order_date, R.id .img, R.id.order_price});
                                ListView list = (ListView) view.findViewById(R.id.listed_order);
                                // set this Adapter to ListView
                                list.setAdapter(simpleAdapter);
                                //bind a listener to ListView
                                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    // 第position项被单击时激发该方法。
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view,
                                                            int position, long id) {
                                        Log.e("Return", "click ok");
                                        //jump to NewOrderActivity
                                        Intent intent = new Intent(getActivity().getApplicationContext(), ViewOrderActivity.class);
                                        Bundle bundle=new Bundle();
                                        bundle.putSerializable("Order", Orders[position]);
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                });
                                list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    // when selected
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view,
                                                               int position, long id) {
                                        Log.e("Return", "select ok");
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        return view;
    }
    private String getDate(long timeStamp){

        try{
            DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date netDate = (new Date(timeStamp));
            return sdf.format(netDate);
        }
        catch(Exception ex){
            return "xx";
        }
    }

}
