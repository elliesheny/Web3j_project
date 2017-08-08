package com.example.aurora.myweb3j;

import java.io.Serializable;

/**
 * Created by yuan.
 */

public class order implements Serializable {
    public int id;
    public int parking_no;
    public int state;
    public double price;
    public long date;
    public String hour_new;
    public order(){
        int id = 0;
        int parking_no = 0;
        int state = 0;
        double price = 0;
        long date = 0;
        String hour_new = null;
    }
}
