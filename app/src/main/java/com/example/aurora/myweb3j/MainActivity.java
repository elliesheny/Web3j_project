package com.example.aurora.myweb3j;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.aurora.myweb3j.contract.ManageOrder;
import com.example.aurora.myweb3j.util.Alice;
import com.example.aurora.myweb3j.util.MyFragmentPagerAdapter;
import com.example.aurora.myweb3j.util.Web3jConstants;
import com.example.aurora.myweb3j.util.Web3jUtils;

import org.web3j.abi.datatypes.Utf8String;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import static com.example.aurora.myweb3j.LoginActivity.web3j;
import static com.example.aurora.myweb3j.RegisterActivity.ADDRESS;
import static com.example.aurora.myweb3j.RegisterActivity.CREDENTIALS;

//main acticity of the application, binds with four fragments
public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener {

    private TextView txt_topbar;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;
    private RadioButton rb_message;
    private RadioButton rb_better;
    private RadioButton rb_setting;
    private ViewPager vpager;

    private MyFragmentPagerAdapter mAdapter;

    //constants that represents the page
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    static ManageOrder contract = null;
    static String order_string;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set the fragment adapter
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        //bind the pages to the fragments
        bindViews();
        //set the first tab to be opened as default
        rb_channel.setChecked(true);
        //send some ether to the account
        BigInteger amountWei = new BigInteger("500000000000000000");
        try {
            String txHash = MainActivity.transferWei(Web3jUtils.getCoinbase(), ADDRESS, amountWei);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //load the contract that is deployed to the ethereum blockchain previously
        try {
            ManageOrder manageOrder = loadContract();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //get the recent order history
        TransactionReceipt result_receipt = null;
        Utf8String result_order = null;
        try {
            result_receipt = MainActivity.contract.listedOrder().get();
            result_order = MainActivity.contract.uintto().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Recent Order Hash: " +result_receipt.getTransactionHash());
        System.out.println("Order Result: " +result_order.getValue());
        order_string = result_order.getValue();

    }

    //bind the radio button and the pages
    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_message = (RadioButton) findViewById(R.id.rb_message);
        rb_better = (RadioButton) findViewById(R.id.rb_better);
        rb_setting = (RadioButton) findViewById(R.id.rb_setting);
        rg_tab_bar.setOnCheckedChangeListener(this);

        vpager = (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }

    //when one radioitem is selected
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            //set the current item as the corresponding page
            case R.id.rb_channel:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.rb_message:
                vpager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.rb_better:
                vpager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.rb_setting:
                vpager.setCurrentItem(PAGE_FOUR);
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    //when swipping the page, the tab switches
    @Override
    public void onPageScrollStateChanged(int state) {
        //three states: 0:nothing done 1:swiping 2:finished swiping
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_channel.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_message.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_better.setChecked(true);
                    break;
                case PAGE_FOUR:
                    rb_setting.setChecked(true);
                    break;
            }
        }
    }


    //load the contract using the user's credentials
    private ManageOrder loadContract() throws Exception {
        System.out.println("// Deploy contract");

        contract = ManageOrder
                .load(Web3jConstants.CONTRACT_ADDRESS, web3j, CREDENTIALS, Web3jConstants.GAS_PRICE, Web3jConstants.GAS_LIMIT_ETHER_TX.multiply(BigInteger.valueOf(2)));

        String contractAddress = contract.getContractAddress();
        System.out.println("Contract address: " + contractAddress);
        //System.out.println("Contract address balance (initial): " + Web3jUtils.getBalanceWei(MainedActivity.web3j, contractAddress));
        return contract;
    }

    //transfer some ehter to the account for further use
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

    //get the nonce of the transaction
    static BigInteger getNonce(String address) throws Exception {
        return Web3jUtils.getNonce(web3j, address);
    }

    //wait for receipt of the transaction
    static TransactionReceipt waitForReceipt(String transactionHash) throws Exception {
        return Web3jUtils.waitForReceipt(web3j, transactionHash);
    }


}
