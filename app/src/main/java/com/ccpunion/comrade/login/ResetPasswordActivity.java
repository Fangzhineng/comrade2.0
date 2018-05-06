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
import com.ccpunion.comrade.databinding.ActivityResetPasswordBinding;
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

public class ResetPasswordActivity extends BaseActivity implements ResultCallBack {

    private ActivityResetPasswordBinding binding;

    private String oneEdit = "";

    private String twoEdit = "";

    private String authCodeEdit = "";

    private String type = "", phone = "";

    @Override
    public void intiLayout() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        binding.setClick(this);
    }

    public static void startActivity(Context context, String type, String phone) {
        Intent intent = new Intent();
        intent.putExtra("type", type);
        intent.putExtra("phone", phone);
        intent.setClass(context, ResetPasswordActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void initData(boolean flag) {
        type = getIntent().getStringExtra("type");
        phone = getIntent().getStringExtra("phone");
        if (type.equals("0")) {
            setTitleName("重置密码");
            binding.authCodeRl.setVisibility(View.VISIBLE);
        } else {
            setTitleName("找回密码");
            binding.authCodeRl.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void initView() {
        back();
        binding.passwordEtOne.addTextChangedListener(oneWatcher);
        binding.passwordEtTwo.addTextChangedListener(twoWatcher);
        binding.authCodeEt.addTextChangedListener(authCodeWatcher);
    }


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
            if (!authCodeEdit.equals("") && !oneEdit.equals("") && !twoEdit.equals("")) {
                binding.submit.setBackground(getResources().getDrawable(R.drawable.button_read_raduis21));
                binding.submit.setEnabled(true);
            } else {
                binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                binding.submit.setEnabled(false);
            }
        }
    };

    TextWatcher oneWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            oneEdit = s.toString().trim();
            if (!oneEdit.equals("")) {
                binding.phoneCancelOne.setVisibility(View.VISIBLE);
                if (type.equals("1")) {
                    if (!twoEdit.equals("")) {
                        binding.submit.setBackground(getResources().getDrawable(R.drawable.button_read_raduis21));
                        binding.submit.setEnabled(true);
                    } else {
                        binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                        binding.submit.setEnabled(false);
                    }
                } else {
                    if (!authCodeEdit.equals("") && !twoEdit.equals("")) {
                        binding.submit.setBackground(getResources().getDrawable(R.drawable.button_read_raduis21));
                        binding.submit.setEnabled(true);
                    } else {
                        binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                        binding.submit.setEnabled(false);
                    }
                }
            } else {
                binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                binding.submit.setEnabled(false);
            }
        }
    };

    TextWatcher twoWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            twoEdit = s.toString().trim();
            if (!twoEdit.equals("")) {
                binding.phoneCancelTwo.setVisibility(View.VISIBLE);
                if (type.equals("1")) {
                    if (!oneEdit.equals("")) {
                        binding.submit.setBackground(getResources().getDrawable(R.drawable.button_read_raduis21));
                        binding.submit.setEnabled(true);
                    } else {
                        binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                        binding.submit.setEnabled(false);
                    }
                } else {
                    if (!authCodeEdit.equals("") && !oneEdit.equals("")) {
                        binding.submit.setBackground(getResources().getDrawable(R.drawable.button_read_raduis21));
                        binding.submit.setEnabled(true);
                    } else {
                        binding.submit.setBackground(getResources().getDrawable(R.drawable.button_gray_raduis21));
                        binding.submit.setEnabled(false);
                    }
                }
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
                //获得验证码
                getAuthCode();
                break;

            case R.id.submit:
                if (type.equals("1")) {
                    if (!oneEdit.equals("") && !twoEdit.equals("")) {
                        if (oneEdit.equals(twoEdit)) {
                            submitData();
                        } else {
                            ToastUtils.showToast(this, "两次密码输入不一致，请重新输入!");
                        }
                    }
                } else {
                    if (!oneEdit.equals("") && !twoEdit.equals("") && !authCodeEdit.equals("")) {
                        if (oneEdit.equals(twoEdit)) {
                            submitData();
                        } else {
                            ToastUtils.showToast(this, "两次密码输入不一致，请重新输入!");
                        }
                    }
                }


                break;

            case R.id.phone_cancel_one:
                binding.passwordEtOne.setText("");
                binding.phoneCancelOne.setVisibility(View.GONE);
                break;
            case R.id.phone_cancel_two:
                binding.passwordEtTwo.setText("");
                binding.phoneCancelTwo.setVisibility(View.GONE);
                break;
        }
    }


    @Override
    public void onSuccess(String response, int flag) {
        if (flag == 1){
            ResetPasswordBean bean = JSONObject.parseObject(response,ResetPasswordBean.class);
            if (bean.getCode().equals("0")){
                oncancel();
                LoginActivity.startActivity(this);
                AppManager.getInstance().killActivity(this);
                ToastUtils.showToast(this,"密码重置成功，请重新登录");
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

    private void submitData() {
        OkHttpUtils.postJsonAsync(this
                , URLConstant.RESET_PASSWORD
                , new RequestParams(this).getResetOrCallBackParams(phone,authCodeEdit,oneEdit,type)
                , this, false, 1);
    }

    private void getAuthCode() {
        OkHttpUtils.postJsonAsync(this
                , URLConstant.GET_AUTH_CODE
                , new RequestParams(this).getAuthCodeParams(phone)
                , this, false, 2);
    }
}
