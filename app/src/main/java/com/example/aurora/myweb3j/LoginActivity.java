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

public class LoginActivity extends AppCompatActivity{
    static Web3j web3j = null;
    static String clientUrl = null;
    static String[] accounts = new String[15];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String TAG = "Return";
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

    public void onLogin(View view){
        Alice.PUBLIC_KEY = readFile("public_key.pem");
        Alice.PRIVATE_KEY = readFile("private_key.pem");

        //Toast.makeText(getApplicationContext(), Alice.PUBLIC_KEY, Toast.LENGTH_LONG).show();
        if((fileExistance("public_key.pem"))&&(fileExistance("private_key.pem"))){
            final Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent_main);
        }else{
            Toast.makeText(getApplicationContext(), "No Record. Please register.", Toast.LENGTH_LONG).show();
        }

    }
    public void onRegister(View view){

        final Intent intent_register = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent_register);

    }
    public void Start_Connect() {
        clientUrl = argsToUrl();
        web3j = Web3jFactory.build(new HttpService(clientUrl));
    }

    public String argsToUrl() {
        String ip = Web3jConstants.CLIENT_IP;
        String port = Web3jConstants.CLIENT_PORT;

        return String.format("http://%s:%s", ip, port);
    }
    public static EthCoinbase getCoinbase(Web3j web3j) throws InterruptedException, ExecutionException {
        return web3j
                .ethCoinbase()
                .sendAsync()
                .get();
    }



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
    public boolean fileExistance(String fname){
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }
}

