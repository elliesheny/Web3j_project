package com.example.aurora.myweb3j.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.aurora.myweb3j.MainActivity;
import com.example.aurora.myweb3j.FindFragment;
import com.example.aurora.myweb3j.OrderFragment;
import com.example.aurora.myweb3j.MyAccountFragment;
import com.example.aurora.myweb3j.ManageFragment;

/**
 * Created by yuan.
 */
//set the fragment layout
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
    private FindFragment findFragment = null;
    private OrderFragment orderFragment = null;
    private MyAccountFragment myAccountFragment = null;
    private ManageFragment manageFragment = null;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        findFragment = new FindFragment();
        orderFragment = new OrderFragment();
        myAccountFragment = new MyAccountFragment();
        manageFragment = new ManageFragment();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    //bind the page to the fragment
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = findFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = orderFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = myAccountFragment;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = manageFragment;
                break;
        }
        return fragment;
    }


}

