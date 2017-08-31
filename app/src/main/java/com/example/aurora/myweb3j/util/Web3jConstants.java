package com.example.aurora.myweb3j.util;

import java.math.BigInteger;

/**
 * Created by yuan.
 */

public class Web3jConstants {
    //connection port
    public static final String CLIENT_IP = "10.97.55.72";//"10.97.174.70";
    public static final String CLIENT_PORT = "8545";

    // see https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);

    // http://ethereum.stackexchange.com/questions/1832/cant-send-transaction-exceeds-block-gas-limit-or-intrinsic-gas-too-low
    public static final BigInteger GAS_LIMIT_ETHER_TX = BigInteger.valueOf(1_000_000);
    public static final BigInteger GAS_LIMIT_WITHBALANCE_TX = BigInteger.valueOf(500_000L);


    public static final int CONFIRMATION_ATTEMPTS = 40;
    public static final int SLEEP_DURATION = 1000;
    public static final String CONTRACT_ADDRESS = "0xce8244d9ac007cf8086a98133ec1b5e69d4bcb66";

}
