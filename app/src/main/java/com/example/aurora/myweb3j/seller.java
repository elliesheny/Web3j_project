package com.example.aurora.myweb3j;

import java.io.Serializable;

/**
 * Created by yuan.
 */

public class seller implements Serializable {

    public int id;//  int(11) AI PK
    public String name;//  varchar(10) PK
    public String avaliable_date_1;//  varchar(24)
    public String avaliable_date_2;//  varchar(24)
    public String avaliable_date_3;//  varchar(24)
    public String phone;//  varchar(14)
    public String post_code;
    public String parking_add;//  varchar(30)
    public seller()
    {
        id = 0;
        name="0";
        avaliable_date_1="000000000000000000000000";
        avaliable_date_2="000000000000000000000000";
        avaliable_date_3="000000000000000000000000";
        phone =null;
        post_code = null;
        parking_add = null;

    }
//    public void setseller(int _id, String _name, String _phone, String _post_code, String _parking_add, String _1, String _2, String _3){
//        this.id = _id;
//        this.name= _name;
//        this.avaliable_date_1= _1;
//        this.avaliable_date_2= _2;
//        this.avaliable_date_3= _3;
//        this.phone =_phone;
//        this.post_code = _post_code;
//        parking_add = _parking_add;
//    }

}
