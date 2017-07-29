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

    /**
     * Returns the balance (in Wei) of the specified account address.
     */
    public static BigInteger getBalanceWei(Web3j web3j, String address) throws InterruptedException, ExecutionException {
        EthGetBalance balance = web3j
                .ethGetBalance(address, DefaultBlockParameterName.LATEST)
                .sendAsync()
                .get();

        return balance.getBalance();
    }


    /**
     * Waits for the receipt for the transaction specified by the provided tx hash.
     * Makes 40 attempts (waiting 1 sec. inbetween attempts) to get the receipt object.
     * In the happy case the tx receipt object is returned.
     * Otherwise, a runtime exception is thrown.
     */
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

    /**
     * Returns the TransactionRecipt for the specified tx hash as an optional.
     */
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

    /**
     * Return the nonce (tx count) for the specified address.
     */
    public static BigInteger getNonce(Web3j web3j, String address) throws InterruptedException, ExecutionException {
        EthGetTransactionCount ethGetTransactionCount =
                web3j.ethGetTransactionCount(address, DefaultBlockParameterName.LATEST).sendAsync().get();

        return ethGetTransactionCount.getTransactionCount();
    }

    static public String getCoinbase() {
        return getAccount(0);
    }
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
