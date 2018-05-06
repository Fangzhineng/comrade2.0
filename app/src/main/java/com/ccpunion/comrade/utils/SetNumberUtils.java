package com.ccpunion.comrade.utils;

/**
 * Created by Administrator on 2018/2/6.
 */

public class SetNumberUtils {

    //雷露说保留一位
    public static String setNumber(double num) {
        double num2;
        String n;
        if (num < 10000) {
            num2 = num;
            n = (int)num2 + "";
        } else {
            double f = num / 10000;
            num2 = ((double) ((int) (f * 10))) / 10;
            n = num2 + "万";
        }
        return n;
    }
}
