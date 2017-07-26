package com.example.aurora.myweb3j;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aurora.myweb3j.contract.ManageOrder;
import com.example.aurora.myweb3j.util.Alice;
import com.example.aurora.myweb3j.util.Web3jConstants;


import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.example.aurora.myweb3j.MainActivity.accounts;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;


public class ContractActivity extends AppCompatActivity {


    ManageOrder contract = null;
    int order_no = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        try {
            ManageOrder manageOrder = loadContract();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //button transfer test
        final Button contract_prepare_ether = (Button) findViewById(R.id.contract_prepare_ether);
        contract_prepare_ether.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BigInteger amountWei = new BigInteger("500000000000000000");
                try {
                    NewAccountActivity.transferWei(NewAccountActivity.getCoinbase(), Alice.ADDRESS, amountWei);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private ManageOrder loadContract() throws Exception {
        System.out.println("// Deploy contract");

        contract = ManageOrder
                .load(Web3jConstants.CONTRACT_ADDRESS, MainActivity.web3j, Alice.CREDENTIALS, Web3jConstants.GAS_PRICE, Web3jConstants.GAS_LIMIT_ETHER_TX.multiply(BigInteger.valueOf(2)));

        String contractAddress = contract.getContractAddress();
        System.out.println("Contract address: " + contractAddress);
        //System.out.println("Contract address balance (initial): " + Web3jUtils.getBalanceWei(MainActivity.web3j, contractAddress));
        return contract;
    }

    public void contract_getbalance(View view) throws ExecutionException, InterruptedException {
        Uint256 result = contract.getBalance(new Address(Alice.ADDRESS)).get();
        System.out.println("Balance from the address: " +result.getValue());
    }




    public void contract_deposit(View view) throws ExecutionException, InterruptedException {

        TransactionReceipt transferreceipt = contract.depositEther(new Uint256(5000)).get();
        System.out.println("Transfer Hash: " + transferreceipt.getTransactionHash());
    }

    public void contract_transfer(View view) throws Exception {
//        Function function = new Function("newOrder", Arrays.<Type>asList(new Address(accounts[0]), new Uint256(250), new Utf8String("111100000000000000000000000000000000000000000000000000000000000000001111")), Collections.<TypeReference<?>>emptyList());
//        String encodedFunction = FunctionEncoder.encode(function);
//        BigInteger nonce = NewAccountActivity.getNonce(NewAccountActivity.getCoinbase());
//        Transaction transaction = Transaction.createFunctionCallTransaction(
//                NewAccountActivity.getCoinbase(),nonce, Web3jConstants.GAS_PRICE, Web3jConstants.GAS_LIMIT_ETHER_TX.multiply(BigInteger.valueOf(2)), Web3jConstants.CONTRACT_ADDRESS, BigInteger.ONE, encodedFunction);
//
//        EthSendTransaction transactionResponse = MainActivity.web3j.ethSendTransaction(transaction).sendAsync().get();
        try {
            testCreateSignAndSendTransaction();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Your code goes here
        TransactionReceipt transferreceipt = contract.newOrder(new Address(accounts[0]),new Uint256(250), new Utf8String("111100000000000000000000000000000000000000000000000000000000000000001111")).get();
        System.out.println("Transfer: " + transferreceipt.getTransactionHash());


    }

    public void contract_new_parking(View view) throws Exception {
        TransactionReceipt transferreceipt = contract.newParking(new Utf8String("Amanda"),new Utf8String("07519857642"),new Utf8String("wc1h 0dp"),new Utf8String("John Adams Hall")).get();

    }
    public void contract_abort(View view) throws Exception {
        EditText editText = (EditText) findViewById(R.id.order_no);
        order_no = Integer.parseInt(editText.getText().toString());

        TransactionReceipt transferreceipt = contract.abortOrder(new Uint256(1)).get();

    }
    public void contract_confirm(View view) throws Exception {
        EditText editText = (EditText) findViewById(R.id.order_no);
        order_no = Integer.parseInt(editText.getText().toString());
        TransactionReceipt transferreceipt = contract.confirmOrder(new Uint256(order_no)).get();

    }
    public void contract_list(View view) throws Exception {
        Uint256 result = contract.listedOrder().get();
        System.out.println("Recent Order No.: " +result.getValue());
    }
    /**
     * Ether transfer tests using methods.
     * Most complex transfer mechanism, but offers the highest flexibility.
     */
    public void testCreateSignAndSendTransaction() throws Exception {
                String from = Alice.ADDRESS;
        Credentials credentials = Alice.CREDENTIALS;
        BigInteger nonce = NewAccountActivity.getNonce(from);
//      String to = Web3jConstants.CONTRACT_ADDRESS;
        BigInteger amountWei = Convert.toWei("0.5", Convert.Unit.ETHER).toBigInteger();

        // funds can be transferred out of the account
        BigInteger txFees = Web3jConstants.GAS_LIMIT_ETHER_TX.multiply(Web3jConstants.GAS_PRICE);
        RawTransaction txRaw = RawTransaction
                .createEtherTransaction(
                        nonce,
                        Web3jConstants.GAS_PRICE,
                        Web3jConstants.GAS_LIMIT_ETHER_TX,
                        Web3jConstants.CONTRACT_ADDRESS,
                        amountWei);

        // sign raw transaction using the sender's credentials
        byte[] txSignedBytes = TransactionEncoder.signMessage(txRaw, credentials);
        String txSigned = Numeric.toHexString(txSignedBytes);

        // send the signed transaction to the ethereum client
        EthSendTransaction ethSendTx = MainActivity.web3j
                .ethSendRawTransaction(txSigned)
                .sendAsync()
                .get();

        //Response.Error error = ethSendTx.getError();
        String txHash = ethSendTx.getTransactionHash();
        //assertNull(error);
        //assertFalse(txHash.isEmpty());

        NewAccountActivity.waitForReceipt(txHash);



    }



}
