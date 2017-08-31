package com.example.aurora.myweb3j;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aurora.myweb3j.util.Seller;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.util.concurrent.ExecutionException;
//create or modify the parking lot
public class NewParkingActivity extends AppCompatActivity {
    private Seller seller_new= new Seller();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_parking);
        //when clicking the button
        Button button_update = (Button) findViewById(R.id.new_parking);
        button_update.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //get user information
                EditText edit_name = (EditText) findViewById(R.id.name);
                seller_new.name = edit_name.getText().toString();
                EditText edit_phone = (EditText) findViewById(R.id.phone);
                seller_new.phone = edit_phone.getText().toString();
                EditText edit_post_code = (EditText) findViewById(R.id.post_code);
                seller_new.post_code = edit_post_code.getText().toString();
                EditText edit_address = (EditText) findViewById(R.id.parking_address);
                seller_new.parking_add = edit_address.getText().toString();

                //send the update request to the contract
                TransactionReceipt transferreceipt = null;
                try {
                    transferreceipt = MainActivity.contract.newParking(new Utf8String(seller_new.name),new Utf8String(seller_new.phone),new Utf8String(seller_new.post_code),new Utf8String(seller_new.parking_add)).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(!transferreceipt.getTransactionHash().isEmpty()){


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            midToast("Ok!", Toast.LENGTH_SHORT);
                            finish();
                        }});
                }

            }
        });

    }

    //personalised toast
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
}
