package com.example.aurora.myweb3j.util;

import java.math.BigInteger;

/**
 * Created by yuan.
 */

public class Web3jConstants {
    public static final String CLIENT_IP = "10.98.7.36";//"10.97.174.70";
    public static final String CLIENT_PORT = "8545";

    // see https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);

    // http://ethereum.stackexchange.com/questions/1832/cant-send-transaction-exceeds-block-gas-limit-or-intrinsic-gas-too-low
    public static final BigInteger GAS_LIMIT_ETHER_TX = BigInteger.valueOf(1_000_000);
    public static final BigInteger GAS_LIMIT_WITHBALANCE_TX = BigInteger.valueOf(500_000L);

    public static final int CONFIRMATION_ATTEMPTS = 40;
    public static final int SLEEP_DURATION = 1000;
    public static final String CONTRACT_ADDRESS = "0xb510107cd90c9370fc5862506d37f555763e5a9a";

    // file name extensions for smart contracts
    public static final String EXT_SOLIDITY = "sol";
    public static final String EXT_BINARY = "bin";
    public static final String EXT_ABI = "abi";
}
