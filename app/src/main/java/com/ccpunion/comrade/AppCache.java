package com.ccpunion.comrade;

/**
 * Created by Administrator on 2018/5/5.
 */

public class AppCache {

    private static boolean isAnswer = false; //每天是否答题


    public static boolean isAnswer() {
        return isAnswer;
    }

    public static void setIsAnswer(boolean isAnswer) {
        AppCache.isAnswer = isAnswer;
    }
}

