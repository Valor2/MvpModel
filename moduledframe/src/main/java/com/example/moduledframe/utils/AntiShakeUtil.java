package com.example.moduledframe.utils;


import java.util.Calendar;

public class AntiShakeUtil {

    public static final int MIN_CLICK_DELAY_TIME = 200;
    public static long lastClickTime = 0;

    public static boolean check() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return false;
        } else {
            return true;
        }
    }
}
