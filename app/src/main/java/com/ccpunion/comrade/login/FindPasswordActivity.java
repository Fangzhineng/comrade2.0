package com.ccpunion.comrade.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.ccpunion.comrade.R;
import com.ccpunion.comrade.base.BaseActivity;
import com.ccpunion.comrade.constant.URLConstant;
import com.ccpunion.comrade.databinding.ActivityFindPasswordBinding;
import com.ccpunion.comrade.http.OkHttpUtils;
import com.ccpunion.comrade.http.RequestParams;
import com.ccpunion.comrade.http.ResultCallBack;
import com.ccpunion.comrade.login.bean.ResetPasswordBean;
import com.ccpunion.comrade.utils.AppManager;
import com.ccpunion.comrade.utils.ToastUtils;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/5/5.
 */

public class FindPasswordActivity extends BaseActivity implements ResultCallBack {
    private ActivityFindPasswordBinding binding;

    private String phoneEdit = "";

    private String authCodeEdit = "";

    @Override
    public void intiLayout() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_password);
        binding.setClick(this);
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, FindPasswordActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initView() {
        back();
        setTitleName("忘记密码");
        binding.phoneEt.addTextChangedListener(phoneWatcher);
        binding.authCodeEt.addTextChangedListener(authCodeWatcher);
    }


    TextWatcher phoneWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            phoneEdit = s.toString().trim();
            if (!phoneEdit.equals("") && !authCodeEdit.equals("")) {
                binding.submit.setBackground(getResources().getDrawable(R.drawable.button_read_raduis21));
                binding.submit.setEnabled(true);
            } else {
                binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                binding.submit.setEnabled(false);
            }
        }
    };

    TextWatcher authCodeWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            authCodeEdit = s.toString().trim();
            if (!phoneEdit.equals("") && !authCodeEdit.equals("")) {
                binding.submit.setBackground(getResources().getDrawable(R.drawable.button_read_raduis21));
                binding.submit.setEnabled(true);
            } else {
                binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                binding.submit.setEnabled(false);
            }
        }
    };

    /**
     * 取消倒计时
     */
    public void oncancel() {
        timer.cancel();
    }

    /**
     * 开始倒计时
     */
    public void restart() {
        timer.start();
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            binding.authCode.setEnabled(false);
            binding.authCode.setText((millisUntilFinished / 1000) + "s后可重发");
            binding.authCode.setBackgroundResource(R.drawable.auth_code_gray);
        }

        @Override
        public void onFinish() {
            binding.authCode.setEnabled(true);
            binding.authCode.setBackgroundResource(R.drawable.auth_code_white);
            binding.authCode.setText("获取验证码");
        }
    };

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.auth_code:
                restart();
                getAuthCode();
                break;

            case R.id.submit:
                if (!authCodeEdit.equals("") && !phoneEdit.equals("")) {
                    checkAuthCode();
                }
                break;
        }
    }

    private void getAuthCode() {
        OkHttpUtils.postJsonAsync(this
                , URLConstant.GET_AUTH_CODE
                , new RequestParams(this).getAuthCodeParams(phoneEdit)
                , this, false, 2);
    }

    private void checkAuthCode() {
        OkHttpUtils.postJsonAsync(this
                , URLConstant.CHECK_AUTH_CODE
                , new RequestParams(this).getCheckParams(phoneEdit, authCodeEdit)
                , this, false, 1);
    }

    @Override
    public void initData(boolean flag) {

    }


    @Override
    public void onSuccess(String response, int flag) {
        if (flag == 1){
            ResetPasswordBean bean = JSONObject.parseObject(response,ResetPasswordBean.class);
            if (bean.getCode().equals("0")){
                oncancel();
                ResetPasswordActivity.startActivity(this, "1", phoneEdit);
                AppManager.getInstance().killActivity(this);
            }else {
                ToastUtils.showToast(this,bean.getMsg());
            }

        }else {
            ResetPasswordBean bean = JSONObject.parseObject(response,ResetPasswordBean.class);
            if (!bean.getCode().equals("0")){
                ToastUtils.showToast(this,bean.getMsg());
            }
        }
    }

    @Override
    public void onFailure(Request request, IOException e, int flag) {

    }
}
