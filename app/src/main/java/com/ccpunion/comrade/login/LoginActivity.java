package com.ccpunion.comrade.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.ccpunion.comrade.MainActivity;
import com.ccpunion.comrade.R;
import com.ccpunion.comrade.base.BaseActivity;
import com.ccpunion.comrade.constant.AppConstant;
import com.ccpunion.comrade.constant.URLConstant;
import com.ccpunion.comrade.databinding.ActivityLoginBinding;
import com.ccpunion.comrade.http.OkHttpUtils;
import com.ccpunion.comrade.http.RequestParams;
import com.ccpunion.comrade.http.ResultCallBack;
import com.ccpunion.comrade.login.bean.LoginBean;
import com.ccpunion.comrade.utils.SharedPreferencesUtils;
import com.ccpunion.comrade.utils.ToastUtils;

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by Administrator on 2018/4/26.
 */

public class LoginActivity extends BaseActivity implements ResultCallBack {

    private boolean lookPassword = false;

    private ActivityLoginBinding binding;

    private String phoneEdit = "";

    private String passwordEdit = "";

    @Override
    public void intiLayout() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setClick(this);
    }


    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        binding.phoneEt.addTextChangedListener(phoneWatcher);
        binding.passwordEt.addTextChangedListener(passwordWatcher);
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
            if (!phoneEdit.equals("")) {
                binding.phoneCancel.setVisibility(View.VISIBLE);
                if (!passwordEdit.equals("")) {
                    binding.submit.setBackground(getResources().getDrawable(R.drawable.button_read_raduis21));
                    binding.submit.setEnabled(true);
                } else {
                    binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                    binding.submit.setEnabled(false);
                }
            } else {
                binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                binding.submit.setEnabled(false);
            }
        }
    };

    TextWatcher passwordWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            passwordEdit = s.toString().trim();
            if (!passwordEdit.equals("")) {
                binding.passwordCancel.setVisibility(View.VISIBLE);
                if (!phoneEdit.equals("")) {
                    binding.submit.setBackground(getResources().getDrawable(R.drawable.button_read_raduis21));
                    binding.submit.setEnabled(true);
                } else {
                    binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                    binding.submit.setEnabled(false);
                }
            } else {
                binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                binding.submit.setEnabled(false);
            }
        }
    };


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_cancel:
                binding.phoneEt.setText("");
                binding.phoneCancel.setVisibility(View.GONE);
                break;
            case R.id.password_cancel:
                binding.passwordEt.setText("");
                binding.passwordCancel.setVisibility(View.GONE);
                binding.lookPassword.setImageResource(R.mipmap.icon_login_eye_close);
                break;
            case R.id.look_password:
                if (lookPassword) {
                    //如果选中，显示密码
                    lookPassword = false;
                    binding.lookPassword.setImageResource(R.mipmap.icon_login_eye_open);
                    binding.passwordEt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    lookPassword = true;
                    binding.lookPassword.setImageResource(R.mipmap.icon_login_eye_close);
                    binding.passwordEt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.forget_password:
                FindPasswordActivity.startActivity(this);
                break;
            case R.id.submit:
                if (!phoneEdit.equals("") && !passwordEdit.equals("")) {
                    submitNews();
                }
                break;
        }

    }

    @Override
    public void initData(boolean flag) {

    }


    private void submitNews() {
        OkHttpUtils.postJsonAsync(this
                , URLConstant.LOGIN
                , new RequestParams(this).getLoginParams(phoneEdit, passwordEdit)
                , this, false, 1);
    }

    @Override
    public void onSuccess(String response, int flag) {
        if (flag == 1) {
            LoginBean bean = JSONObject.parseObject(response, LoginBean.class);
            if (bean.getCode().equals("0")) {
                if (null != bean.getBody()) {
                    SharedPreferencesUtils.putBoolean(this, AppConstant.IS_LOGIN, true);
                    SharedPreferencesUtils.putString(this, AppConstant.COMMUNIST_ID, String.valueOf(bean.getBody().getCommunistId()));
                    SharedPreferencesUtils.putString(this, AppConstant.ORG_NAME, bean.getBody().getOrgName());
                    SharedPreferencesUtils.putString(this, AppConstant.ORG_CHAIN_ID, bean.getBody().getOrgChainId());
                    SharedPreferencesUtils.putString(this, AppConstant.ORG_ID, String.valueOf(bean.getBody().getOrgId()));
                    SharedPreferencesUtils.putString(this, AppConstant.NAME, bean.getBody().getName());
                    SharedPreferencesUtils.putString(this, AppConstant.ACCOUNT, bean.getBody().getAccount());
                    SharedPreferencesUtils.putString(this, AppConstant.PHONE, bean.getBody().getPhone());

                    MainActivity.startActivity(this);
                }
            } else if (bean.getCode().equals("666")) {
                ToastUtils.showToast(this, "为了您的账号安全首次登录请重置密码!");
                ResetPasswordActivity.startActivity(this, "0", phoneEdit);
            } else {
                ToastUtils.showToast(this, bean.getMsg());
            }

        }
    }

    @Override
    public void onFailure(Request request, IOException e, int flag) {
        SharedPreferencesUtils.putBoolean(this, AppConstant.IS_LOGIN, false);
    }
}
