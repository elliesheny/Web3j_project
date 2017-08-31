package com.example.aurora.myweb3j;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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

import com.example.aurora.myweb3j.util.Day;
import com.example.aurora.myweb3j.util.MyAdapter;
import com.example.aurora.myweb3j.util.Seller;

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

//select the occupied hour for the parking lot
public class ManageParkingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    private byte[] hour_buffer = new byte[24];
    private CheckBox[] check_hour = new CheckBox[24];
    private int[] checkbox_id = new int[24];
    private Button btn_book;
    private Seller me_seller= new Seller();
    private String day_selected = "0";
    private ArrayList<Day> mData = null;
    private Spinner spin_day;
    private BaseAdapter myAdadpter = null;
    private String hour_selected = "";
    private String hour_new = "";
    private Utf8String result_parking = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_parking);
        mData = new ArrayList<Day>();
        bindViews();

        //get the information of user's parking lot
        try {
            result_parking = MainActivity.contract.numParking().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Parking Info: " +result_parking.getValue());

        //if the user hasn't create any parking lot, guide the user to create one
        if((result_parking+"").isEmpty()){
            Toast.makeText(getApplicationContext(), "Please new a parking area first.", Toast.LENGTH_LONG).show();

        }else {
            //if the user has created one, save the information to a seller class
            String coded_result = result_parking.getValue().substring(0, result_parking.getValue().indexOf('%'));
            //decode the string responce
            String temp = coded_result.substring(0, coded_result.indexOf('*'));
            me_seller.name = temp;
            coded_result = coded_result.substring(coded_result.indexOf('*') + 1);

            temp = coded_result.substring(0, coded_result.indexOf('*'));
            me_seller.phone = temp;
            coded_result = coded_result.substring(coded_result.indexOf('*') + 1);

            temp = coded_result.substring(0, coded_result.indexOf('*'));
            me_seller.post_code = temp.substring(0, temp.length() - 1);
            ;
            coded_result = coded_result.substring(coded_result.indexOf('*') + 1);

            temp = coded_result.substring(0, coded_result.indexOf('*'));
            //separate the available hours to hours in the next three days
            Log.d("hours", temp);
            me_seller.available_date_1 = temp.substring(0, 24);
            me_seller.available_date_2 = temp.substring(24, 48);
            me_seller.available_date_3 = temp.substring(48, 72);
            Log.d("avail_hour", me_seller.available_date_1 + "and" + me_seller.available_date_2);
            coded_result = coded_result.substring(coded_result.indexOf('*') + 1);
            me_seller.parking_add = coded_result.substring(0, coded_result.indexOf('*'));
            coded_result = coded_result.substring(coded_result.indexOf('*') + 1);
            me_seller.id = Integer.parseInt(coded_result.substring(1));
            Log.d("result", coded_result.substring(1));
            //save the available hour in one day
            hour_buffer = me_seller.available_date_1.getBytes();
            //initiate the state of the check box
            for (int i = 0; i < check_hour.length; i++) {
                String strcheckID = "checkbox" + i;
                checkbox_id[i] = getResources().getIdentifier(strcheckID, "id", "com.example.aurora.myweb3j");
                check_hour[i] = (CheckBox) findViewById(checkbox_id[i]);
                check_hour[i].setOnCheckedChangeListener(this);
                if (hour_buffer[i] == '1') {
                    check_hour[i].setEnabled(false);
                } else {
                    check_hour[i].setEnabled(true);
                }
            }
            btn_book = (Button) findViewById(R.id.btn_book);
            btn_book.setOnClickListener(this);
        }


    }

    //when one item in the spinner is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spin_day:
                //save the selected day
                day_selected = ""+position;
                if(!result_parking.getValue().isEmpty()){
                    if(position==0)
                    {
                        //set the checkbox status in one day
                        hour_buffer= me_seller.available_date_1.getBytes();
                        for(int i = 0; i<check_hour.length; i++) {
                            String strcheckID = "checkbox" + i;
                            checkbox_id[i] = getResources().getIdentifier(strcheckID,"id","com.example.aurora.myweb3j");
                            check_hour[i] = (CheckBox) findViewById(checkbox_id[i]);
                            Log.d("Checkbox: ",""+checkbox_id[i]);
                            if(hour_buffer[i]=='1') {
                                check_hour[i].setEnabled(false);
                            }
                            else{
                                check_hour[i].setEnabled(true);
                            }
                        }
                    }
                    else if(position==1)
                    {
                        //if the second day is selected
                        hour_buffer= me_seller.available_date_2.getBytes();
                        for(int i = 0; i<check_hour.length; i++) {
                            String strcheckID = "checkbox" + i;
                            checkbox_id[i] = getResources().getIdentifier(strcheckID,"id","com.example.aurora.myweb3j");
                            check_hour[i] = (CheckBox) findViewById(checkbox_id[i]);
                            if(hour_buffer[i]=='1') {
                                check_hour[i].setEnabled(false);
                            }
                            else{
                                check_hour[i].setEnabled(true);
                            }
                        }
                    }
                    else if(position==2)
                    {
                        //the third day is selected
                        hour_buffer= me_seller.available_date_3.getBytes();
                        for(int i = 0; i<check_hour.length; i++) {
                            String strcheckID = "checkbox" + i;
                            checkbox_id[i] = getResources().getIdentifier(strcheckID,"id","com.example.aurora.myweb3j");
                            check_hour[i] = (CheckBox) findViewById(checkbox_id[i]);
                            if(hour_buffer[i]=='1') {
                                check_hour[i].setEnabled(false);
                            }
                            else{
                                check_hour[i].setEnabled(true);
                            }
                        }
                    }
                    break;
                }
        }
    }

    //bind the days to the it day spinner
    private void bindViews() {
        spin_day = (Spinner) findViewById(R.id.spin_day);
        //set the display format of the date
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        //get the today's date
        Calendar today = Calendar.getInstance();
        String date_1 = df.format(today.getTime());
        //get the next day
        today.add(today.DATE,1);
        String date_2 = df.format(today.getTime());
        //get the third day
        today.add(today.DATE,1);
        String date_3 = df.format(today.getTime());
        //add the day to the spinner item
        mData.add(new Day(R.drawable.day,date_1));
        mData.add(new Day(R.drawable.day,date_2));
        mData.add(new Day(R.drawable.day,date_3));

        //set the adapter
        myAdadpter = new MyAdapter<Day>(mData,R.layout.item_spin_day) {
            @Override
            public void bindView(ViewHolder holder, Day obj) {
                holder.setImageResource(R.id.img_icon,obj.gethIcon());
                holder.setText(R.id.txt_day, obj.gethDay());
            }
        };
        spin_day.setAdapter(myAdadpter);
        spin_day.setOnItemSelectedListener(this);

    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    //change the toast interface
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

    //when "book" button is clicked
    @Override
    public void onClick(View v) {
        int checked_no = 0;
        hour_selected = "";
        hour_new = "";

        //count the booking hour slots
        for(int i = 0; i<check_hour.length; i++) {
            if (check_hour[i].isChecked())
            {
                hour_selected += "1";
                hour_new+="1";
                checked_no++;
            }else if(!check_hour[i].isEnabled()){
                hour_selected += "1";
                hour_new+="0";
            }
            else{
                hour_selected += "0";
                hour_new+="0";
            }
        }

        //for a selected day, copy the information into buffer
        if(day_selected.equals("0"))
        {
            me_seller.available_date_1 = hour_selected;
            hour_new = hour_new + "000000000000000000000000"+"000000000000000000000000";
        }else if(day_selected.equals("1"))
        {
            me_seller.available_date_2 = hour_selected;
            hour_new = "000000000000000000000000"+hour_new+"000000000000000000000000";
        }else{
            me_seller.available_date_3 = hour_selected;
            hour_new = "000000000000000000000000"+"000000000000000000000000"+hour_new;
        }
        //concat three days together
        String avail_date = me_seller.available_date_1.concat(me_seller.available_date_2).concat(me_seller.available_date_3);
        Log.d("Debug","Avail hour: "+avail_date);
        //send request to the contract
        if(checked_no!=0)
        {

            TransactionReceipt transferreceipt = null;
            try {
                transferreceipt = contract.manageParking(new Uint256(me_seller.id),new Utf8String(avail_date)).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //when getting a receipt, notice the user
            if(!transferreceipt.getTransactionHash().isEmpty()){


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        midToast("Book Completed!", Toast.LENGTH_SHORT);
                        finish();
                    }});
            }
        }
        else{
            midToast("Please select booking hours!",Toast.LENGTH_SHORT);
        }
    }


}
