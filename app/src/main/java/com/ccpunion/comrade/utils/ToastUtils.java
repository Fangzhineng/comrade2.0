package com.ccpunion.comrade.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\  =  /O
 * ____/`---'\____
 * .'  \\|     |//  `.
 * /  \\|||  :  |||//  \
 * /  _||||| -:- |||||-  \
 * |   | \\\  -  /// |   |
 * | \_|  ''\---/''  |   |
 * \  .-\__  `-`  ___/-. /
 * ___`. .'  /--.--\  `. . __
 * ."" '<  `.___\_<|>_/___.'  >'"".
 * | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 * \  \ `-.   \_ __\ /__ _/   .-` /  /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * 佛祖保佑        永无BUG
 */

public class ToastUtils {

    public static Toast mToast;

    public static void showToast(Context context, String toastText, int duration) {
        if (mToast != null) {
            mToast.setText(toastText);
            mToast.setDuration(duration);
            mToast.show();
        } else {
            mToast = Toast.makeText(context, toastText, duration);
            mToast.show();
        }
    }

    public static void showToast(Context context, String toastText) {
        if (mToast != null) {
            mToast.setText(toastText);
            mToast.setDuration(mToast.LENGTH_SHORT);
            mToast.show();
        } else {
            mToast = Toast.makeText(context, toastText, mToast.LENGTH_SHORT);
            mToast.show();
        }
    }

    public static void showToast(Context context, int resId) {
        if (mToast != null) {
            mToast.setText(context.getText(resId));
            mToast.setDuration(mToast.LENGTH_SHORT);
            mToast.show();
        } else {
            mToast = Toast.makeText(context, context.getText(resId), mToast.LENGTH_SHORT);
            mToast.show();
        }
    }

}
