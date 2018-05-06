package com.ccpunion.comrade;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;

import com.ccpunion.comrade.base.BaseActivity;
import com.ccpunion.comrade.constant.AppConstant;
import com.ccpunion.comrade.databinding.ActivityWelcomeBinding;
import com.ccpunion.comrade.http.ResultCallBack;
import com.ccpunion.comrade.login.EveryDayProblemAct;
import com.ccpunion.comrade.login.LoginActivity;
import com.ccpunion.comrade.utils.SharedPreferencesUtils;
import com.ccpunion.comrade.utils.StatusBarCompat;

import java.io.IOException;

import okhttp3.Request;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity implements ResultCallBack {

    private static final String TAG = "WelcomeActivity";

    private boolean customSplash = false;

    private static boolean firstEnter = true; // 是否首次进入

    ActivityWelcomeBinding binding;

    @Override
    public void intiLayout() {
        StatusBarCompat.compat(this, Color.parseColor("#00000000"));
        binding = DataBindingUtil.setContentView(this, R.layout.activity_welcome);
    }

    @Override
    public void initView() {
        if (!(SharedPreferencesUtils.getString(this, AppConstant.COMMUNIST_ID) == null)) {
            if (!SharedPreferencesUtils.getString(this, AppConstant.COMMUNIST_ID).equals("")) {
                //判断是否答题
                //isAnswerQuestion();

                toWelcomOrMainActivity();
            } else {
                toWelcomOrMainActivity();
            }
        } else {
            toWelcomOrMainActivity();
        }
    }

    @Override
    public void initData(boolean flag) {
        //初始位""
        SharedPreferencesUtils.putString(this, AppConstant.COMMUNIST_ID, "");
    }


    /**
     * 是否启动引导页
     */
    private void toWelcomOrMainActivity() {
//        boolean isFirstRun =true;
//        String versionName = AppUtils.getVersionName(this);
//        if (null != SharedPreferencesUtils.getString(this, AppConstant.LAUNCHE_SPLASH_FLAG)) {
//            isFirstRun = !SharedPreferencesUtils.getString(this, AppConstant.LAUNCHE_SPLASH_FLAG).equals(versionName);
//        }
//        if (isFirstRun) {
//            Intent intent = new Intent(this, GuideActivity.class);
//            startActivity(intent);
//        } else {

        //临时设置为true
        AppCache.setIsAnswer(true);
        if (SharedPreferencesUtils.getBoolean(this, AppConstant.IS_LOGIN, false)) {
            if (AppCache.isAnswer()) {
                startActivity(new Intent(this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, EveryDayProblemAct.class));
            }
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }

//        }
//        SharedPreferencesUtils.putString(this, AppConstant.LAUNCHE_SPLASH_FLAG, versionName);
        SplashActivity.this.finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void isAnswerQuestion() {
//        OkHttpUtils.postAsync(this
//                , URLConstant.MANAGER_PROBLRMS_ISANSWER
//                , new RequestParams(this).getParams()
//                , this, false, 1);
    }

    @Override
    public void onSuccess(String response, int flag) {
//        EveryIsAnswerBean bean = JSON.parseObject(response, EveryIsAnswerBean.class);
//        if (bean.getCode().equals("0")) {
//            if (bean.getBody().getExist() == 0) {
//                AppCache.setIsAnswer(false);
//            } else {
//                AppCache.setIsAnswer(true);
//            }
//        } else {
//            ToastUtils.showToast(this,  bean.getMsg());
//        }
//        toWelcomOrMainActivity();
    }

    @Override
    public void onFailure(Request request, IOException e, int flag) {
        toWelcomOrMainActivity();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
