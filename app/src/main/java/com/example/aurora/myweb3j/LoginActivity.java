package com.example.aurora.myweb3j;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.aurora.myweb3j.util.Alice;
import com.example.aurora.myweb3j.util.Web3jConstants;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

//user login activity
public class LoginActivity extends AppCompatActivity{
    static public Web3j web3j = null;
    static String clientUrl = null;
    static String[] accounts = new String[15];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String TAG = "Return";

        //connect to the ethereum client node
        Start_Connect();
        // show client details
        Web3ClientVersion client = null;
        try {
            client = web3j
                    .web3ClientVersion()
                    .sendAsync()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Connected to " + client.getWeb3ClientVersion() + "\n");


    }

    //when clicking "login" button
    public void onLogin(View view){
        //read the key pairs from the internal file
        Alice.PUBLIC_KEY = readFile("public_key.pem");
        Alice.PRIVATE_KEY = readFile("private_key.pem");

        //if the file exists, jump to the main activity
        if((fileExistance("public_key.pem"))&&(fileExistance("private_key.pem"))){
            final Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent_main);
        }else{
            //if not exist, guide the user to the register interface
            Toast.makeText(getApplicationContext(), "No Record. Please register.", Toast.LENGTH_LONG).show();
        }

    }

    //when uer clicking the "register" button
    public void onRegister(View view){

        final Intent intent_register = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent_register);

    }

    //connect the moible device to the ethereum client node
    public void Start_Connect() {
        clientUrl = argsToUrl();
        web3j = Web3jFactory.build(new HttpService(clientUrl));
    }

    //connection port and ip
    public String argsToUrl() {
        String ip = Web3jConstants.CLIENT_IP;
        String port = Web3jConstants.CLIENT_PORT;

        return String.format("http://%s:%s", ip, port);
    }

    //get the coinbase of the ethereum wallet
    public static EthCoinbase getCoinbase(Web3j web3j) throws InterruptedException, ExecutionException {
        return web3j
                .ethCoinbase()
                .sendAsync()
                .get();
    }


    //read the file from the internal database
    public String readFile(String filename) {
        FileInputStream inputStream;
        try
        {
            inputStream = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(inputStream); BufferedReader bufferedReader = new BufferedReader(isr); StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    //check whether the file exists or not
    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}

