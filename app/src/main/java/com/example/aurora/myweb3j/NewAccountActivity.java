package com.example.aurora.myweb3j;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.protocol.core.Response;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.aurora.myweb3j.MainActivity;
import com.example.aurora.myweb3j.util.Web3jConstants;
import com.example.aurora.myweb3j.util.Web3jUtils;

import static com.example.aurora.myweb3j.MainActivity.web3j;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

public class NewAccountActivity extends AppCompatActivity {
    static final String ERROR = "Error";
    static final String RETURN = "Return";
    static String publicKeyHex = null;
    static String privateKeyHex = null;
    static String  address = null;
    BigInteger nonce = null;
    String txHash = null;
    static Credentials credentials = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        BigInteger amountWei = new BigInteger("300");
        // create new private/public key pair
        final ECKeyPair[] keyPair = {null};
        //button transfer
        final Button button_keypair = (Button) findViewById(R.id.get_keypair);
        button_keypair.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                try {
                    keyPair[0] = Keys.createEcKeyPair();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    Log.e(ERROR,"No Such Algorithm Exception");
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                    Log.e(ERROR,"No Such Provider Exception");
                }

                BigInteger publicKey = keyPair[0].getPublicKey();
                publicKeyHex = Numeric.toHexStringWithPrefix(publicKey);

                BigInteger privateKey = keyPair[0].getPrivateKey();
                privateKeyHex = Numeric.toHexStringWithPrefix(privateKey);

                // create credentials + address from private/public key pair
                credentials = Credentials.create(new ECKeyPair(privateKey, publicKey));
                address = credentials.getAddress();

                // print resulting data of new account
                Log.d(RETURN, "private key: '" + privateKeyHex + "'");
                Log.d(RETURN, "public key: '" + publicKeyHex + "'");
                Log.d(RETURN, "address: '" + address + "'\n");
            }
        });

        //button transfer test
        final Button button_transfer = (Button) findViewById(R.id.transfer_to_new);
        button_transfer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                // test (1) check if it's possible to transfer funds to new address

                try {
                    transferWei(getCoinbase(), address, amountWei);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                BigInteger balanceWei = null;
                try {
                    balanceWei = getBalanceWei(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    nonce = getNonce(address);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                assertEquals("Unexpected nonce for 'to' address", BigInteger.ZERO, nonce);
                assertEquals("Unexpected balance for 'to' address", amountWei, balanceWei);

            }
        });

        //button transfer out test
        final Button button_transfer_out = (Button) findViewById(R.id.transfer_out);
        button_transfer_out.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button

                // test (2) funds can be transferred out of the newly created account
                BigInteger txFees = Web3jConstants.GAS_LIMIT_ETHER_TX.multiply(Web3jConstants.GAS_PRICE);
                RawTransaction txRaw = RawTransaction
                        .createEtherTransaction(
                                nonce,
                                Web3jConstants.GAS_PRICE,
                                Web3jConstants.GAS_LIMIT_ETHER_TX,
                                getCoinbase(),
                                amountWei.subtract(txFees));

                // sign raw transaction using the sender's credentials
                byte[] txSignedBytes = TransactionEncoder.signMessage(txRaw, credentials);
                String txSigned = Numeric.toHexString(txSignedBytes);

                // send the signed transaction to the ethereum client
                EthSendTransaction ethSendTx = null;
                try {
                    ethSendTx = web3j
                            .ethSendRawTransaction(txSigned)
                            .sendAsync()
                            .get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Response.Error error = ethSendTx.getError();
                txHash = ethSendTx.getTransactionHash();
                assertNull(error);
                assertFalse(txHash.isEmpty());

                try {
                    waitForReceipt(txHash);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    assertEquals("Unexpected nonce for 'to' address", BigInteger.ONE, getNonce(address));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    assertTrue("Balance for 'from' address too large: " + getBalanceWei(address), getBalanceWei(address).compareTo(txFees) < 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



    }
    static String transferWei(String from, String to, BigInteger amountWei) throws Exception {
        BigInteger nonce = getNonce(from);
        Transaction transaction = Transaction.createEtherTransaction(
                from, nonce, Web3jConstants.GAS_PRICE, Web3jConstants.GAS_LIMIT_ETHER_TX, to, amountWei);

        EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).sendAsync().get();
        System.out.println("transferWei. nonce: " + nonce + " amount: " + amountWei + " to: " + to);

        String txHash = ethSendTransaction.getTransactionHash();
        waitForReceipt(txHash);

        return txHash;
    }
    static BigInteger getNonce(String address) throws Exception {
        return Web3jUtils.getNonce(web3j, address);
    }

    static String getCoinbase() {
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
    static BigInteger getBalanceWei(String address) throws Exception {
        return Web3jUtils.getBalanceWei(web3j, address);
    }
    static TransactionReceipt waitForReceipt(String transactionHash) throws Exception {
        return Web3jUtils.waitForReceipt(web3j, transactionHash);
    }

    public void contract_activity(View view) {

        Intent intent = new Intent(this, ContractActivity.class);

        startActivity(intent);
    }




}
