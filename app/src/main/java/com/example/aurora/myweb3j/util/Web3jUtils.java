package com.example.aurora.myweb3j.util;

import com.example.aurora.myweb3j.LoginActivity;
import com.example.aurora.myweb3j.MainActivity;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static com.example.aurora.myweb3j.LoginActivity.web3j;

/**
 * Created by yuan.
 */

public class Web3jUtils {

    //returns the balance
    public static BigInteger getBalanceWei(Web3j web3j, String address) throws InterruptedException, ExecutionException {
        EthGetBalance balance = web3j
                .ethGetBalance(address, DefaultBlockParameterName.LATEST)
                .sendAsync()
                .get();

        return balance.getBalance();
    }


    //send the signed transaction and wair for receipt
    public static TransactionReceipt waitForReceipt(Web3j web3j, String transactionHash)
            throws Exception
    {

        int attempts = Web3jConstants.CONFIRMATION_ATTEMPTS;
        int sleep_millis = Web3jConstants.SLEEP_DURATION;

        Optional<TransactionReceipt> receipt = getReceipt(web3j, transactionHash);

        while(attempts-- > 0 && !receipt.isPresent()) {
            Thread.sleep(sleep_millis);
            receipt = getReceipt(web3j, transactionHash);
        }

        if (attempts <= 0) {
            throw new RuntimeException("No Tx receipt received");
        }

        return receipt.get();
    }

    //return the receipt of the transaction
    public static Optional<TransactionReceipt> getReceipt(Web3j web3j, String transactionHash)
            throws Exception
    {
        EthGetTransactionReceipt receipt = web3j
                .ethGetTransactionReceipt(transactionHash)
                .sendAsync()
                .get();
        Optional<TransactionReceipt> receipt_now = Optional.of(receipt.getTransactionReceipt());

        return receipt_now;
    }

    //get the nonce of the transaction
    public static BigInteger getNonce(Web3j web3j, String address) throws InterruptedException, ExecutionException {
        EthGetTransactionCount ethGetTransactionCount =
                web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();

        return ethGetTransactionCount.getTransactionCount();
    }

    //get the coinbase of the ethereum wallet
    static public String getCoinbase() {
        return getAccount(0);
    }

    //get the account from web3j
    static String getAccount(int i) {
        try {
            EthAccounts accountsResponse = web3j.ethAccounts().sendAsync().get();
            List<String> accounts = accountsResponse.getAccounts();

            return accounts.get(i);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return "<no address>";
        }
    }

}
