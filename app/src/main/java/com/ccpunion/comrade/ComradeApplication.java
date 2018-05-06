package com.ccpunion.comrade;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Administrator on 2018/4/24.
 */

public class ComradeApplication extends Application {

    private static final String TAG = "ComradeApplication";
    public static ComradeApplication myApplication = null;

    public static Application getInstance() {
        if (myApplication == null) {
            myApplication = new ComradeApplication();
        }
        return myApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(ComradeApplication.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //保存当前程序的实例对象
        myApplication = this;
    }
}
