package com.ccpunion.comrade.http;

import android.content.Context;
import android.util.Log;

import com.ccpunion.comrade.constant.AppConstant;
import com.ccpunion.comrade.utils.HttpUtils;
import com.ccpunion.comrade.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/25.
 */

public class RequestParams {

    public String TAG = "RequestParams";

    /***
     * 登录ID
     */
    public String communistId = "", orgChainId, orgName, name, date;

    public RequestParams(Context context) {
        super();
        if (SharedPreferencesUtils.getBoolean(context, AppConstant.IS_LOGIN, false)) {
//            communistId = SharedPreferencesUtil.getString(context,Constants.USER_ID,"");
            communistId = SharedPreferencesUtils.getString(context, AppConstant.COMMUNIST_ID);
            orgChainId = SharedPreferencesUtils.getString(context, AppConstant.ORG_CHAIN_ID);
            orgName = SharedPreferencesUtils.getString(context, AppConstant.ORG_NAME);
            name = SharedPreferencesUtils.getString(context, AppConstant.NAME);
            date = HttpUtils.getCurDateStr("yyyy-MM-dd HH:mm:ss");
            Log.d("tag", "communistId = " + communistId);
        }
    }

    /**
     * 通用参数请求
     */
    public Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        return params;
    }

    /**
     * 登录
     */
    public Map<String, String> getLoginParams(String username,String password) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username",username);
        params.put("password",password);
        return params;
    }

    /**
     * 获取验证码
     */
    public Map<String, String> getAuthCodeParams(String phone) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone",phone);
        return params;
    }

    /**
     * 验证验证码
     */
    public Map<String, String> getCheckParams(String phone,String smscode) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone",phone);
        params.put("smscode",smscode);
        return params;
    }
    /**
     * 重置找回密码
     */
    public Map<String, String> getResetOrCallBackParams(String phone,String smscode,String password,String type) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("phone",phone);
        params.put("smscode",smscode);
        params.put("password",password);
        params.put("type",type);
        return params;
    }
}
