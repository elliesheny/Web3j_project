package com.example.aurora.myweb3j;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import com.example.aurora.myweb3j.util.Alice;
import com.example.aurora.myweb3j.util.Web3jConstants;

import com.example.aurora.myweb3j.util.Web3jUtils;

public class MainActivity extends AppCompatActivity {



    static Web3j web3j = null;
    static String clientUrl = null;
    static String[] accounts = new String[15];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String TAG = "Return";
        Start_Connect();
        TextView balanceTextView = (TextView) findViewById(R.id.account_balance);

        // show client details
        Web3ClientVersion client = null;
        try {
            client = web3j
                    .web3ClientVersion()
                    .sendAsync()
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, "debug 0");
        } catch (ExecutionException e) {
            e.printStackTrace();
            Log.d(TAG, "debug 1");
        }
        Log.d(TAG, "Connected to " + client.getWeb3ClientVersion() + "\n");
        Log.d(TAG, "Connected to " + Alice.ADDRESS + "\n");

        //button get_balance
        final Button button_balance = (Button) findViewById(R.id.get_balance);
        button_balance.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //get accounts
                EthAccounts get_accounts = null;
                try {
                    get_accounts = getAccounts(web3j);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                int account_size = get_accounts.getAccounts().size();
                //String[] accounts = new String[account_size];
                for(int i=0; i<account_size; i++) {
                    accounts[i] = get_accounts.getAccounts().get(i);
                }
                int account_no = 2;
                Log.d(TAG, "Accounts:" + accounts[account_no] + "\n");

                //get balance
                BigDecimal balance_in_ether = null;

                try {
                    balance_in_ether = getBalanceEther(web3j, accounts[account_no]);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Balance:" + balance_in_ether + "\n");


            }
        });


        //button transfer
        final Button button_transfer = (Button) findViewById(R.id.transfer_wei);
        button_transfer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                //transaction
                TransactionReceipt transaction_receipt= null;
                BigInteger amountWei = new BigInteger("300");
                try {
                    transaction_receipt = transferFromCoinbaseAndWait(web3j,accounts[2],amountWei);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Transaction hash:" + transaction_receipt.getTransactionHash()+ "\n");

            }
        });


    }

    public void new_account(View view) {

            // Code here executes on main thread after user presses button
            Intent intent = new Intent(this, NewAccountActivity.class);
//            EditText editText = (EditText) findViewById(R.id.editText);
//            String message = editText.getText().toString();
//            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);


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

    /**
     * Returns the list of addresses owned by this client.
     */
    public static EthAccounts getAccounts(Web3j web3j) throws InterruptedException, ExecutionException {
        return web3j
                .ethAccounts()
                .sendAsync()
                .get();
    }

    /**
     * Returns the balance (in Ether) of the specified account address.
     */
    public static BigDecimal getBalanceEther(Web3j web3j, String address) throws InterruptedException, ExecutionException {
        return weiToEther(Web3jUtils.getBalanceWei(web3j, address));
    }




    /**
     * Converts the provided Wei amount (smallest value Unit) to Ethers.
     */
    public static BigDecimal weiToEther(BigInteger wei) {
        return Convert.fromWei(wei.toString(), Convert.Unit.ETHER);
    }

    public static BigInteger etherToWei(BigDecimal ether) {
        return Convert.toWei(ether, Convert.Unit.ETHER).toBigInteger();
    }




    /**
     * Transfers the specified amount of Wei from the coinbase to the specified account.
     * The method waits for the transfer to complete using method {@link}.
     */
    public static TransactionReceipt transferFromCoinbaseAndWait(Web3j web3j, String to, BigInteger amountWei)
            throws Exception
    {
        String coinbase = getCoinbase(web3j).getResult();
        BigInteger nonce = Web3jUtils.getNonce(web3j, coinbase);
        // this is a contract method call -> gas limit higher than simple fund transfer
        BigInteger gasLimit = Web3jConstants.GAS_LIMIT_ETHER_TX.multiply(BigInteger.valueOf(2));
        Transaction transaction = Transaction.createEtherTransaction(
                coinbase,
                nonce,
                Web3jConstants.GAS_PRICE,
                gasLimit,
                to,
                amountWei);

        EthSendTransaction ethSendTransaction = web3j
                .ethSendTransaction(transaction)
                .sendAsync()
                .get();

        String txHash = ethSendTransaction.getTransactionHash();

        return Web3jUtils.waitForReceipt(web3j, txHash);
    }



}
