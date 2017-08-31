package com.example.aurora.myweb3j.util;

/**
 * Created by yuan.
 */

//the day adapter
public class Day {
    private int hIcon;
    private String hDay;

    public Day() {
    }

    public Day(int hIcon, String hDay) {
        this.hIcon = hIcon;
        this.hDay = hDay;
    }

    public int gethIcon() {
        return hIcon;
    }

    public String gethDay() {
        return hDay;
    }

    public void sethIcon(int hIcon) {
        this.hIcon = hIcon;
    }

    public void sethDay(String hDay) {
        this.hDay = hDay;
    }
}
