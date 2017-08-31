package com.example.aurora.myweb3j;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import com.example.aurora.myweb3j.util.Alice;
import com.example.aurora.myweb3j.util.Day;
import com.example.aurora.myweb3j.util.MyAdapter;
import com.example.aurora.myweb3j.util.Seller;
import com.example.aurora.myweb3j.util.Web3jConstants;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import static com.example.aurora.myweb3j.MainActivity.contract;
import static com.example.aurora.myweb3j.RegisterActivity.ADDRESS;
import static com.example.aurora.myweb3j.RegisterActivity.CREDENTIALS;

//create a new order
public class NewOrderActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    private Seller seller_selected= new Seller();
    private Spinner spin_day;
    private Context mContext;
    private ArrayList<Day> mData = null;
    private BaseAdapter myAdadpter = null;
    private CheckBox[] check_hour = new CheckBox[24];
    private int[] checkbox_id = new int[24];
    private Button btn_book;
    private String hour_selected = "";
    private String hour_new = "";
    private String day_selected = "0";
    private String book_send;
    private byte[] hour_buffer = new byte[24];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Intent intent = getIntent();
        seller_selected=(Seller)intent.getSerializableExtra("Seller");
        toolbar.setTitle(seller_selected.parking_add);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        mData = new ArrayList<Day>();
        bindViews();

        //display seller's information
        TextView textName = (TextView) findViewById(R.id.seller_select_name);
        textName.setText(seller_selected.name);
        TextView textPhone = (TextView) findViewById(R.id.seller_select_phone);
        textPhone.setText(seller_selected.phone);
        TextView textAddress = (TextView) findViewById(R.id.seller_select_address);
        textAddress.setText(seller_selected.parking_add);
        TextView textPostCode = (TextView) findViewById(R.id.seller_select_post_code);
        textPostCode.setText(seller_selected.post_code);


        hour_buffer= seller_selected.available_date_1.getBytes();

        //set the state of each checkbox
        for(int i = 0; i<check_hour.length; i++) {
            String strcheckID = "checkbox" + i;
            checkbox_id[i] = getResources().getIdentifier(strcheckID,"id","com.example.aurora.myweb3j");
            check_hour[i] = (CheckBox) findViewById(checkbox_id[i]);
            check_hour[i].setOnCheckedChangeListener(this);
            if(hour_buffer[i]=='1') {
                check_hour[i].setEnabled(false);
            }
            else{
                check_hour[i].setEnabled(true);
            }
        }
        btn_book = (Button) findViewById(R.id.btn_book);
        btn_book.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    //bind the spinner with days
    private void bindViews() {
        spin_day = (Spinner) findViewById(R.id.spin_day);
        //set the day format
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
        Calendar today = Calendar.getInstance();
        String date_1 = df.format(today.getTime());
        today.add(today.DATE,1);
        String date_2 = df.format(today.getTime());
        today.add(today.DATE,1);
        String date_3 = df.format(today.getTime());
        mData.add(new Day(R.drawable.day,date_1));
        mData.add(new Day(R.drawable.day,date_2));
        mData.add(new Day(R.drawable.day,date_3));

        //bind the day with the spinner
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

    //when the hour is selected
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.spin_day:
                day_selected = ""+position;
                //set the checked hours in the selected day
                if(position==0)
                {
                    hour_buffer= seller_selected.available_date_1.getBytes();
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
                //if the second day is selected
                else if(position==1)
                {
                    hour_buffer= seller_selected.available_date_2.getBytes();
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
                //if the third day is selected
                else if(position==2)
                {
                    hour_buffer= seller_selected.available_date_3.getBytes();
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

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //when the "book" button is selected
    @Override
    public void onClick(View v) {
        int checked_no = 0;
        hour_selected = "";
        hour_new = "";
        //count the selected hours in a day
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
        if(day_selected.equals("0"))
        {
            seller_selected.available_date_1 = hour_selected;
            hour_new = hour_new + "000000000000000000000000"+"000000000000000000000000";
        }else if(day_selected.equals("1"))
        {
            seller_selected.available_date_2 = hour_selected;
            hour_new = "000000000000000000000000"+hour_new+"000000000000000000000000";
        }else{
            seller_selected.available_date_3 = hour_selected;
            hour_new = "000000000000000000000000"+"000000000000000000000000"+hour_new;
        }
        String avail_date = seller_selected.available_date_1.concat(seller_selected.available_date_2).concat(seller_selected.available_date_3);
        Log.d("Debug","Avail hour: "+avail_date);
        Log.d("Debug","No: "+checked_no);

        //calculate the parking fee and send it to the contract
        BigInteger amountWei = new BigInteger("10000000000000000").multiply(BigInteger.valueOf(checked_no));
        if(checked_no!=0)
        {
            try {
                testCreateSignAndSendTransaction(amountWei);
                Log.d("Debug","About to Transfer: " + amountWei);

            } catch (Exception e) {
                e.printStackTrace();
            }
            TransactionReceipt transferreceipt = null;
            try {
                transferreceipt = contract.newOrder(new Uint256(seller_selected.id),new Uint256(amountWei), new Utf8String(avail_date),new Utf8String(hour_new)).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            System.out.println("Transfer: " + transferreceipt.getTransactionHash());
            //when a receipt received
            if(!transferreceipt.getTransactionHash().isEmpty()){


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    midToast("Book Finished!",Toast.LENGTH_SHORT);
                    finish();
                }});
            }
        }
        else{
            midToast("Please select booking hours!",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }
    //set a customised toast
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

    //sign transaction and wait for receipt
    public void testCreateSignAndSendTransaction(BigInteger amountWei) throws Exception {
        String from = ADDRESS;
        Credentials credentials = CREDENTIALS;
        BigInteger nonce = MainActivity.getNonce(from);

        //create raw transaction
        BigInteger txFees = Web3jConstants.GAS_LIMIT_ETHER_TX.multiply(Web3jConstants.GAS_PRICE);
        RawTransaction txRaw = RawTransaction
                .createEtherTransaction(
                        nonce,
                        Web3jConstants.GAS_PRICE,
                        Web3jConstants.GAS_LIMIT_ETHER_TX,
                        Web3jConstants.CONTRACT_ADDRESS,
                        amountWei);

        // sign the raw transaction
        byte[] txSignedBytes = TransactionEncoder.signMessage(txRaw, credentials);
        String txSigned = Numeric.toHexString(txSignedBytes);

        // send the signed transaction
        EthSendTransaction ethSendTx = LoginActivity.web3j
                .ethSendRawTransaction(txSigned)
                .sendAsync()
                .get();

        String txHash = ethSendTx.getTransactionHash();


        MainActivity.waitForReceipt(txHash);



    }
}
