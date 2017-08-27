package com.example.aurora.myweb3j;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aurora.myweb3j.util.Order;

import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.aurora.myweb3j.MainActivity.contract;

public class ViewOrderActivity extends AppCompatActivity{
    private Order order_selected= new Order();
    private Context mContext;
    private BaseAdapter myAdadpter = null;
    private Button btn_abort;
    private String book_send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        order_selected=(Order)intent.getSerializableExtra("Order");
        toolbar.setTitle("Order id: "+ order_selected.id);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);



        //convert unix epoch timestamp (seconds) to milliseconds
        long timestamp = Long.parseLong(order_selected.date+"") * 1000L;

        TextView textName = (TextView) findViewById(R.id.order_select_date);
        textName.setText(getDate(timestamp));
        TextView textPhone = (TextView) findViewById(R.id.order_select_price);
        textPhone.setText(order_selected.price+"");
        TextView textAddress = (TextView) findViewById(R.id.order_select_state);
        String state = null;
        switch (order_selected.state) {
            case 1:
                state = "Created";
                break;
            case 2:
                state = "Pending";
                break;
            case 3:
                state = "Completed";
                break;
            case 4:
                state = "Aborted";
                break;
        }
        textAddress.setText(state);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    public void midToast(String str, int showTime)
    {
        Toast toast = Toast.makeText(getApplicationContext(), str, showTime);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM , 0, 0);  //set the position to display
        LinearLayout layout = (LinearLayout) toast.getView();
        layout.setBackgroundColor(getResources().getColor(R.color.div_green));
        ImageView image = new ImageView(this);
        image.setImageResource(R.mipmap.check_ok);
        layout.addView(image, 0);
        toast.setGravity(Gravity.CENTER, 0, 0);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(getResources().getColor(R.color.text_yellow));     //set the color
        toast.show();
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

    public void abort_order(View view) throws Exception {
        TransactionReceipt transferreceipt = contract.abortOrder(new Uint256(order_selected.id)).get();
        System.out.println("Abort hash: " +transferreceipt.getTransactionHash());
        if(!transferreceipt.getTransactionHash().isEmpty()){


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    midToast("Aborted!",Toast.LENGTH_SHORT);
                    finish();
                }});
        }
    }
    public void confirm_order(View view) throws Exception {

        TransactionReceipt transferreceipt = contract.confirmOrder(new Uint256(order_selected.id)).get();
        System.out.println("Confirm hash: " +transferreceipt.getTransactionHash());
        if(!transferreceipt.getTransactionHash().isEmpty()){


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    midToast("Confirmed!",Toast.LENGTH_SHORT);
                    finish();
                }});
        }
    }
}
