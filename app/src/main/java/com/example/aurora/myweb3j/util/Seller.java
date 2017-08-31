package com.example.aurora.myweb3j.util;

import java.io.Serializable;

/**
 * Created by yuan.
 */
//define the seller structure
public class Seller implements Serializable {

    public int id;//  int(11) AI PK
    public String name;//  varchar(10) PK
    public String available_date_1;//  varchar(24)
    public String available_date_2;//  varchar(24)
    public String available_date_3;//  varchar(24)
    public String phone;//  varchar(14)
    public String post_code;
    public String parking_add;//  varchar(30)

    public Seller()
    {
        id = 0;
        name="0";
        available_date_1="000000000000000000000000";
        available_date_2="000000000000000000000000";
        available_date_3="000000000000000000000000";
        phone =null;
        post_code = null;
        parking_add = null;

    }


}
