package com.ccpunion.comrade.utils;

import android.content.Context;
import android.view.WindowManager;

import com.ccpunion.comrade.ComradeApplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import static android.media.MediaExtractor.MetricsConstants.FORMAT;

/**
 * Created by Administrator on 2018/4/24.
 */

public class HttpUtils {
    /**
     * @param isWidth true获得宽，false获得高
     * @return
     */
    public static int getHightorWidth(boolean isWidth) {
        WindowManager wm = (WindowManager) ComradeApplication.getInstance().getSystemService(Context.WINDOW_SERVICE);
        return isWidth ? wm.getDefaultDisplay().getWidth() : wm.getDefaultDisplay().getHeight();
    }


    public static String changeFE(String str) {
        return ((char) (str.charAt(str.length() - 1) - 1)) + str.substring(1, str.length() - 1) + ((char) (str.charAt(0) + 1));
    }

    /**
     * 获得当前日期的字符串格式
     *
     * @param format
     * @return
     */
    public static String getCurDateStr(String format) {
        Calendar c = Calendar.getInstance();
        return date2Str(c, format);
    }


    public static String date2Str(Calendar c, String format) {
        if (c == null) {
            return null;
        }
        return date2Str(c.getTime(), format);
    }

    public static String date2Str(Date d, String format) {// yyyy-MM-dd HH:mm:ss
        if (d == null) {
            return null;
        }
        if (format == null || format.length() == 0) {
            format = FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(d);
    }


    /**
     * 获得系统时间戳
     *
     * @return
     */
    public static String getTimeStamp() {
        long time = System.currentTimeMillis() ;
        String str = String.valueOf(time);
        return str;
    }

    /**
     * @param p
     * @return
     */
    public static int getRandom(int p) {
        Random random = new Random();
        int number = 0;
        if (p == 3) {
            return random.nextInt(999) % (999 - 0 + 1) + 0;
        } else if (p == 4) {
            return random.nextInt(9999) % (9999 - 0 + 1) + 0;
        } else if (p == 5) {
            return random.nextInt(99999) % (99999 - 0 + 1) + 0;
        } else if (p == 6) {
            number = random.nextInt(999999) % (999999 - 0 + 1) + 0;
            return number;
        } else {
            return 0;
        }
    }

    public static String getMyUUID() {
        UUID uuid = UUID.randomUUID();
        String uniqueId = uuid.toString();
        return uniqueId;
    }


    public static int dip2px(float dipValue) {
        final float scale = ComradeApplication.getInstance().getResources().getDisplayMetrics().densityDpi;
        return (int) (dipValue * (scale / 160) + 0.5f);
    }

    public static int px2dp(float pxValue) {
        final float scale = ComradeApplication.getInstance().getResources().getDisplayMetrics().densityDpi;
        return (int) ((pxValue * 160) / scale + 0.5f);
    }


}
