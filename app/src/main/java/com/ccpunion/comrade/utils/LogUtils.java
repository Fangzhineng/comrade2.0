package com.ccpunion.comrade.utils;

import android.util.Log;


public class LogUtils {

    /**
     * Primary log taglv_tag for games output.
     */
    private static final String LOG_TAG = "LogUtils";

    /**
     * Whether the logs are enabled in release builds or not.
     */
    private static final boolean ENABLE_LOGS_IN_RELEASE = false;

    public static boolean canLog(int level) {
//		return (ENABLE_LOGS_IN_RELEASE || BuildConfig.DEBUG)
//				&& Log.isLoggable(LOG_TAG, level);

//		return false;
        return true;
    }

    public static void d(String tag, String message) {
        if (canLog(Log.DEBUG)) {
            Log.d(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (canLog(Log.VERBOSE)) {
            Log.v(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (canLog(Log.INFO)) {
            Log.i(tag, message);
        }
    }

    public static void i(String tag, String message, Throwable thr) {
        if (canLog(Log.INFO)) {
            Log.i(tag, message, thr);
        }
    }

    public static void w(String tag, String message) {
        if (canLog(Log.WARN)) {
            Log.w(tag, message);
        }
    }

    public static void w(String tag, String message, Throwable thr) {
        if (canLog(Log.WARN)) {
            Log.w(tag, message, thr);
        }
    }

    public static void e(String tag, String message) {
        if (canLog(Log.ERROR)) {
            Log.e(tag, message);
        }
    }

    public static void e(String tag, String message, Throwable thr) {
        if (canLog(Log.ERROR)) {
            Log.e(tag, message, thr);
        }
    }
}
