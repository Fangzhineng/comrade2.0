package com.ccpunion.comrade.http;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.ccpunion.comrade.R;
import com.ccpunion.comrade.constant.AppConstant;
import com.ccpunion.comrade.constant.HttpConstant;
import com.ccpunion.comrade.dialog.CustomProgressDialog;
import com.ccpunion.comrade.utils.HttpUtils;
import com.ccpunion.comrade.utils.LogUtils;
import com.ccpunion.comrade.utils.SharedPreferencesUtils;
import com.ccpunion.comrade.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/4/25.
 */

public class OkHttpUtils {

    private final String TAG = "OkHttpUtils";

    private Handler handler;
    private OkHttpClient httpClient;

    private static OkHttpUtils instance;
    private static final int DEFAULT_TIMEOUT = 8000;
//    public static final long DEFAULT_MILLISECONDS = 30000;
//    public static final long DEFAULT_READ_TIMEOUT_MILLISECONDS = 15000;
//    public static final long DEFAULT_WRITE_TIMEOUT_MILLISECONDS = 15000;

    private static CustomProgressDialog customProgressDialog;


    private OkHttpUtils(final Context context) {
        this.httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = null;
                        try {
                            request = chain.request()
                                    .newBuilder()
                                    .addHeader("OAUTH-PWD", AESUtil.encrypt(HttpConstant.KEY, HttpConstant.CODE + "," + HttpUtils.getTimeStamp() + "," + HttpUtils.getRandom(6)))
                                    .addHeader("OUATH-COMMUNSIT", AESUtil.decrypt(HttpConstant.DECRYPT_KEY, SharedPreferencesUtils.getString(context, AppConstant.COMMUNIST_ID)))
                                    .build();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return chain.proceed(request);
                    }
                }).retryOnConnectionFailure(true)
                .build();
        this.handler = new Handler(Looper.getMainLooper());
    }


    public static OkHttpUtils getInstance(Context context) {

        if (instance == null) {
            synchronized (OkHttpUtils.class) {
                if (instance == null) {
                    instance = new OkHttpUtils(context);
                    return instance;
                }
            }
        }
        return instance;
    }

    public OkHttpClient getHttpClient() {
        return httpClient;
    }

    private static boolean isNetWorkConnected(Context context,
                                              ResultCallBack callBack) {

        if (NetWorkUtil.isNetWorkConnected(context)) {
            return true;
        }

        ToastUtils.showToast(context, R.string.toast_network_connection_tips);
        if (callBack != null) {
            callBack.onFailure(null, null, -2);
        }


        return false;
    }

    /***
     * 异步POST请求
     *
     * @param context     上下文
     * @param url         请求URL
     * @param params      请求参数
     * @param callBack    回调函数
     * @param loadingView 页面加载控件
     * @param contentView 页面显示控件
     * @param isLoading   是否显示加载控件
     */
    public static void postAsync(Context context, String url,
                                 Map<String, String> params, ResultCallBack callBack,
                                 View loadingView, View contentView, boolean isLoading) {
        if (!isNetWorkConnected(context, callBack)) {

            if (loadingView != null && contentView != null) {
                loadingView.setVisibility(View.VISIBLE);
                contentView.setVisibility(View.GONE);
            }

            return;
        }
        getInstance(context)._postAsync(context, url, params, callBack, loadingView,
                contentView, isLoading);
    }

    /***
     * 异步POST请求
     *
     * @param context       上下文
     * @param url           请求地址
     * @param params        请求参数
     * @param callBack      回调函数
     * @param isShowLoading 是否显示Loading
     * @param flag          请求标识
     */
    public static void postAsync(Context context, String url,
                                 Map<String, String> params, ResultCallBack callBack,
                                 boolean isShowLoading, int flag) {
        if (!isNetWorkConnected(context, callBack)) {
            return;
        }
        getInstance(context)._postAsync(context, url, params, callBack,
                getDialog(context, isShowLoading), flag, 1);
    }

    /***
     * 异步POST请求
     *
     * @param context     上下文
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    回调函数
     * @param loadingText 加载进度条提示文本
     * @param flag        请求标识
     */
    public static void postAsync(Context context, String url,
                                 Map<String, String> params, ResultCallBack callBack,
                                 int loadingText, int flag) {
        if (!isNetWorkConnected(context, callBack)) {
            return;
        }
        getInstance(context)._postAsync(
                context,
                url,
                params,
                callBack,
                getDialog(context, true,
                        context.getResources().getString(loadingText)), flag, 1);


    }

    /***
     * 异步POST请求
     *
     * @param context     上下文
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    回调函数
     * @param loadingText 加载进度条提示文本
     * @param flag        请求标识
     */
    public static void postJsonAsync(Context context, String url,
                                     Map<String, String> params, ResultCallBack callBack,
                                     int loadingText, int flag) {
        if (!isNetWorkConnected(context, callBack)) {
            return;
        }
        getInstance(context)._postJsonAsync(
                context,
                url,
                params,
                callBack,
                getDialog(context, true,
                        context.getResources().getString(loadingText)), flag, 1);


    }


    /***
     * 异步POST请求
     *
     * @param context     上下文
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    回调函数
     * @param isShowDialog
     * @param flag        请求标识
     */
    public static void postJsonAsync(Context context, String url,
                                     Map<String, String> params, ResultCallBack callBack,
                                     boolean isShowDialog, int flag) {
        if (!isNetWorkConnected(context, callBack)) {
            return;
        }
        getInstance(context)._postJsonAsync(
                context,
                url,
                params,
                callBack,
                getDialog(context, isShowDialog), flag, 1);


    }

    /***
     * 异步POST请求
     *
     * @param context     上下文
     * @param url         请求地址
     * @param params      请求参数
     * @param callBack    回调函数
     * @param loadingText 加载进度条提示文本
     * @param flag        请求标识
     */
    public static void postJsonOAsync(Context context, String url,
                                      Map<String, Object> params, ResultCallBack callBack,
                                      int loadingText, int flag) {
        if (!isNetWorkConnected(context, callBack)) {
            return;
        }
        getInstance(context)._postJsonOAsync(
                context,
                url,
                params,
                callBack,
                getDialog(context, true,
                        context.getResources().getString(loadingText)), flag, 1);


    }

    public static void postAsyncNoDialog(Context context, String url,
                                         Map<String, String> params, ResultCallBack callBack,
                                         int flag) {
        if (!isNetWorkConnected(context, callBack)) {
            return;
        }

        getInstance(context)._postAsyncNoDialog(
                context,
                url,
                params,
                callBack,
                flag, 1);


    }

    /***
     * 异步POST请求
     *
     * @param context  上下文
     * @param url      请求地址
     * @param params   请求参数
     * @param callBack 回调函数
     * @param flag     请求标识
     */
    public static void postAsync(Context context, String url,
                                 Map<String, String> params, ResultCallBack callBack, int flag) {
        if (!isNetWorkConnected(context, callBack)) {
            return;
        }
        getInstance(context)._postAsync(context, url, params, callBack, null, flag, 1);
    }

    /***
     * 异步POST请求
     *
     * @param context  上下文
     * @param url      请求地址
     * @param params   请求参数
     * @param callBack 回调函数
     */
    public static void postAsync(Context context, String url,
                                 Map<String, String> params, ResultCallBack callBack) {
        if (!isNetWorkConnected(context, callBack)) {
            return;
        }
        getInstance(context)._postAsync(context, url, params, callBack, null, 1, 1);
    }


    /***
     * 异步POST请求上传文件
     *
     * @param context
     * @param url
     * @param params
     * @param callBack
     * @param fileParam
     * @param isShowLoading
     * @param flag
     */
    public static void postUploadFileAsync(Context context, String url,
                                           Map<String, String> params, ResultCallBack callBack, Map<String, File> fileParam,
                                           boolean isShowLoading, int flag) {
        if (!isNetWorkConnected(context, callBack)) {
            return;
        }
        getInstance(context)._postAsync(context, url, params, callBack, fileParam,
                getDialog(context, isShowLoading), flag);
    }


    /***
     * 请求POST
     *
     * @param context       上下文
     * @param url           请求URL
     * @param params        请求参数
     * @param callBack      回调函数
     * @param loadingDialog 加载进度框
     * @param flag          请求标识
     * @param type          请求类型 1、懒腰模块	2、信用模块
     */
    private void _postAsync(Context context, String url,
                            Map<String, String> params, ResultCallBack callBack,
                            Dialog loadingDialog, int flag, int type) {

        Param[] paramsArr = map2Params(params);
        Request request = getPostRequest(url, paramsArr);
        if (request == null) {
            ToastUtils.showToast(context, R.string.toast_network_abnormal_loading_failure);
            callBack.onFailure(null, null, -2);
            return;
        }
        deliveryResult(context, request, callBack, loadingDialog, flag, type);
    }

    /***
     * 请求POST
     *
     * @param context       上下文
     * @param url           请求URL
     * @param params        请求参数
     * @param callBack      回调函数
     * @param loadingDialog 加载进度框
     * @param flag          请求标识
     * @param type          请求类型 1、懒腰模块	2、信用模块
     */
    private void _postJsonAsync(Context context, String url,
                                Map<String, String> params, ResultCallBack callBack,
                                Dialog loadingDialog, int flag, int type) {

        Request request = getPostJsonRequest(url, params);
        // Log.e("OkHttpUtiles", String.valueOf(request));
        if (request == null) {
            ToastUtils.showToast(context, R.string.toast_network_abnormal_loading_failure);
            callBack.onFailure(null, null, -2);
            return;
        }
        deliveryResult(context, request, callBack, loadingDialog, flag, type);
    }

    /***
     * 请求POST
     *
     * @param context       上下文
     * @param url           请求URL
     * @param params        请求参数
     * @param callBack      回调函数
     * @param loadingDialog 加载进度框
     * @param flag          请求标识
     * @param type          请求类型 1、懒腰模块	2、信用模块
     */
    private void _postJsonOAsync(Context context, String url,
                                 Map<String, Object> params, ResultCallBack callBack,
                                 Dialog loadingDialog, int flag, int type) {

        Request request = getPostJsonORequest(url, params);
        // Log.e("OkHttpUtiles", String.valueOf(request));
        if (request == null) {
            ToastUtils.showToast(context, R.string.toast_network_abnormal_loading_failure);
            callBack.onFailure(null, null, -2);
            return;
        }
        deliveryResult(context, request, callBack, loadingDialog, flag, type);
    }

    private void _postAsyncNoDialog(Context context, String url,
                                    Map<String, String> params, ResultCallBack callBack,
                                    int flag, int type) {
        Param[] paramsArr = map2Params(params);
        Request request = getPostRequest(url, paramsArr);
        if (request == null) {
            ToastUtils.showToast(context, R.string.toast_network_abnormal_loading_failure);
            callBack.onFailure(null, null, -2);
            return;
        }
        deliveryResultNoDialog(context, request, callBack, flag, type);
    }


    private void _postAsync(Context context, String url,
                            Map<String, String> params, ResultCallBack callBack,
                            View loadingView, View contentView, boolean isLoading) {
        Param[] paramsArr = map2Params(params);
        Request request = getPostRequest(url, paramsArr);
        if (request == null) {
            ToastUtils.showToast(context, R.string.toast_network_abnormal_loading_failure);
            if (loadingView != null && contentView != null) {
                loadingView.setVisibility(View.VISIBLE);
                contentView.setVisibility(View.GONE);
            }
            callBack.onFailure(null, null, -2);
            return;
        }
        deliveryResult(context, request, callBack, loadingView, contentView, 1, isLoading);
    }


    private void _postAsync(Context context, String url,
                            Map<String, String> params, ResultCallBack callBack, Map<String, File> fileParam,
                            Dialog loadingDialog, int flag) {
        Param[] paramsArr = map2Params(params);
        Request request = buildMultipartFormRequests(url, fileParam, paramsArr);
        deliveryResult(context, request, callBack, loadingDialog, flag, 1);
    }

    private void deliveryResult(final Context context, final Request request,
                                final ResultCallBack callBack, final View loadingView,
                                final View contentView, final int flag, final boolean isLoading) {

        if (loadingView != null && contentView != null && isLoading) {
            loadingView.setVisibility(View.VISIBLE);
            contentView.setVisibility(View.GONE);
        }

        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // TODO Auto-generated method stub
                sendFailureCallBack(context, request, e, callBack, loadingView,
                        contentView);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                final int code = response.isSuccessful() ? 200 : 500;

                String result1 = null;
                try {
                    result1 = AESUtil.decrypt(HttpConstant.DECRYPT_KEY, result);
                    LogUtils.i(TAG + " 1 ", result1);
                    sendSuccessCallBack(context, result1, callBack, loadingView, contentView, code);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }

    /***
     * 请求服务返回结果
     *
     * @param context       上下文
     * @param request       请求Request
     * @param callBack      回调函数
     * @param loadingDialog 加载进度框
     * @param flag          请求标识
     * @param type          请求类型 1、懒腰模块	2、信用模块
     */
    private void deliveryResult(final Context context, final Request request,
                                final ResultCallBack callBack, final Dialog loadingDialog,
                                final int flag, final int type) {
        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                // TODO Auto-generated method stub
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                if (type == 2) {
                    sendCreditFailureCallBack(context, request, e, callBack, flag);
                } else {
                    sendFailureCallBack(context, request, e, callBack, flag);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // TODO Auto-generated method stub
                if (loadingDialog != null && loadingDialog.isShowing()) {
                    loadingDialog.dismiss();
                }
                final String result = response.body().string();
                final int code = response.isSuccessful() ? 200 : 500;
                String result1 = null;
                try {
                    result1 = AESUtil.decrypt(HttpConstant.DECRYPT_KEY, result);
                    LogUtils.i(TAG + " 2 ", result1);
                    if (type == 2) {
                        sendCreditSuccessCallBack(context, result1, callBack, flag, code);
                    } else {
                        sendSuccessCallBack(context, result1, callBack, flag, code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });
    }

    private void deliveryResultNoDialog(final Context context, final Request request,
                                        final ResultCallBack callBack,
                                        final int flag, final int type) {
        httpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                if (type == 2) {
                    sendCreditFailureCallBack(context, request, e, callBack, flag);
                } else {
                    sendFailureCallBack(context, request, e, callBack, flag);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                final int code = response.isSuccessful() ? 200 : 500;
                String result1 = null;
                try {
                    result1 = AESUtil.decrypt(HttpConstant.DECRYPT_KEY, result);
                    LogUtils.i(TAG + " 3 ", result1);
                    if (type == 2) {
                        sendCreditSuccessCallBack(context, result1, callBack, flag, code);
                    } else {
                        sendSuccessCallBack(context, result1, callBack, flag, code);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });
    }

    private void sendSuccessCallBack(final Context context,
                                     final String response, final ResultCallBack callBack,
                                     final View loadingView, final View contentView, final int statusCode) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (callBack != null) {
                    if (loadingView != null && contentView != null) {
                        loadingView.setVisibility(View.GONE);
                        contentView.setVisibility(View.VISIBLE);
                    }
                    if (statusCode != 200) {
                        ToastUtils.showToast(context,
                                context.getResources().getString(R.string.toast_network_abnormal_loading_failure));
                        callBack.onFailure(null, null, 1);
                        return;
                    }

                    try {
                        ErrMsg err = JSON.parseObject(response, ErrMsg.class);
                        if (err != null) {
                            if (err.getErr() != null) {
                                if (!TextUtils.isEmpty(err.getErr()
                                        .getErrMessage())) {
                                    ToastUtils.showToast(context,
                                            err.getErr().getErrMessage());
                                }
                                callBack.onFailure(null, null, 1);
                                return;
                            }
                        }
                        callBack.onSuccess(response, 1);
                    } catch (Exception e) {

                        callBack.onSuccess(response, 1);
                    }

                }
            }
        });
    }

    private void sendSuccessCallBack(final Context context,
                                     final String response, final ResultCallBack callBack, final int flag, final int statusCode) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (callBack != null) {

                    if (statusCode != 200) {
                        ToastUtils.showToast(context,
                                context.getResources().getString(R.string.toast_network_abnormal_loading_failure));
                        callBack.onFailure(null, null, 1);
                        return;
                    }

                    try {
                        ErrMsg err = JSON.parseObject(response, ErrMsg.class);
                        if (err != null) {
                            if (err.getErr() != null) {
                                if (!TextUtils.isEmpty(err.getErr()
                                        .getErrMessage())) {
                                    ToastUtils.showToast(context,
                                            err.getErr().getErrMessage());
                                }
                                callBack.onFailure(null, null, flag);
                                return;
                            }
                        }
                        callBack.onSuccess(response, flag);
                    } catch (Exception e) {
                        callBack.onSuccess(response, flag);
                    }

                }
            }
        });
    }

    private void sendFailureCallBack(final Context context, final Request request,
                                     final IOException e, final ResultCallBack callBack,
                                     final View loadingView, final View contentView) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (callBack != null) {
                    if (loadingView != null && contentView != null) {
                        loadingView.setVisibility(View.VISIBLE);
                        contentView.setVisibility(View.GONE);
                    }
                    ToastUtils.showToast(context, R.string.toast_network_abnormal_loading_failure);
                    callBack.onFailure(null, null, -2);
                }
            }
        });
    }

    private void sendFailureCallBack(final Context context,
                                     final Request request, final IOException e,
                                     final ResultCallBack callBack, final int flag) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (callBack != null) {
                    ToastUtils.showToast(context, R.string.toast_network_abnormal_loading_failure);
                    callBack.onFailure(request, e, flag);
                }
            }
        });
    }

    /***
     * 信用模块成功回调
     *
     * @param context
     * @param response
     * @param callBack
     * @param flag
     * @param statusCode
     */
    private void sendCreditSuccessCallBack(final Context context,
                                           final String response, final ResultCallBack callBack, final int flag, final int statusCode) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (callBack != null) {

                    if (statusCode != 200) {
                        ToastUtils.showToast(context,
                                context.getResources().getString(R.string.toast_network_abnormal_loading_failure));
                        callBack.onFailure(null, null, 1);
                        return;
                    }

                    try {
                        ErrMsg err = JSON.parseObject(response, ErrMsg.class);
                        if (err != null) {
                            if (err.getErr() != null) {
                                if (!TextUtils.isEmpty(err.getErr()
                                        .getErrMessage())) {
                                    //									ToastUtilss.getInstance().makeText(context,err.getErr().getErrMessage());
                                    ToastUtils.showToast(context, err.getErr().getErrMessage());
                                }
                                callBack.onFailure(null, null, flag);
                                return;
                            }
                        }

                        callBack.onSuccess(response, flag);
                    } catch (Exception e) {
                        callBack.onSuccess(response, flag);
                    }

                }
            }
        });
    }

    /***
     * 信用模块失败回调
     *
     * @param context
     * @param request
     * @param e
     * @param callBack
     * @param flag
     */
    private void sendCreditFailureCallBack(final Context context,
                                           final Request request, final IOException e,
                                           final ResultCallBack callBack, final int flag) {
        handler.post(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (callBack != null) {
                    ToastUtils.showToast(context, R.string.toast_network_abnormal_loading_failure);
                    callBack.onFailure(request, e, flag);
                }
            }
        });
    }

    /**
     * 图片上传
     *
     * @param url
     * @param fileParam
     * @param params
     * @return
     */
    private Request buildMultipartFormRequests(String url, Map<String, File> fileParam,
                                               Param[] params) {
        params = validateParam(params);

        if (fileParam == null) {
            return null;
        }

        MultipartBody.Builder requestBuild = new MultipartBody.Builder().setType(MultipartBody.FORM);

        Iterator<Map.Entry<String, File>> entries = fileParam.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, File> entry = entries.next();
            Log.i("tag", "fileKey = " + entry.getKey());
            Log.i("tag", "fileName = " + entry.getValue().getName());
            String fileName = entry.getValue().getName();
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), entry.getValue());
            requestBuild.addFormDataPart(entry.getKey(), fileName, fileBody);
        }

        for (Param param : params) {
            requestBuild.addFormDataPart(param.key, param.value);
        }
        RequestBody requestBody = requestBuild.build();

        return new Request.Builder().url(url).post(requestBody).build();
    }


    private Request getPostRequest(String url, Param[] params) {
        try {
            if (params == null) {
                params = new Param[0];
            }
            FormBody.Builder builder = new FormBody.Builder();
            for (Param param : params) {
                builder.add(param.key, param.value);

            }
            RequestBody body = builder.build();
            return new Request.Builder().url(url).post(body).build();
        } catch (Exception e) {
            return null;
        }

    }

    private Request getPostJsonRequest(String url, Map<String, String> params) {
        try {
            LogUtils.i(TAG, JSON.toJSONString(params));
            MediaType contentType
                    = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(contentType, JSON.toJSONString(params));
            return new Request.Builder().url(url).post(body).build();
        } catch (Exception e) {
            return null;
        }

    }

    private Request getPostJsonORequest(String url, Map<String, Object> params) {
        try {
            LogUtils.i(TAG, JSON.toJSONString(params));
            MediaType contentType
                    = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(contentType, JSON.toJSONString(params));
            return new Request.Builder().url(url).post(body).build();
        } catch (Exception e) {
            return null;
        }

    }

    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else
            return params;
    }

    private Param[] map2Params(Map<String, String> params) {
        if (params == null)
            return new Param[0];
        int size = params.size();
        Param[] res = new Param[size];
        Set<Map.Entry<String, String>> entries = params.entrySet();
        int i = 0;
        for (Map.Entry<String, String> entry : entries) {
            res[i++] = new Param(entry.getKey(), entry.getValue());
        }
        return res;
    }

    public static class Param {
        public Param() {
        }

        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        String key;
        String value;
    }

    public static Dialog getDialog(Context context, boolean isShowDialog,
                                   String promptTxt) {
        customProgressDialog = CustomProgressDialog.createDialog(context);
        customProgressDialog.setMessage(promptTxt);
        if (isShowDialog) {
            customProgressDialog.show();
        }
        return customProgressDialog;
    }

    public static Dialog getDialog(Context context, boolean isShowDialog) {

        if (null == customProgressDialog) {
            customProgressDialog = CustomProgressDialog.createDialog(context);
        }
        customProgressDialog.setCancelable(false);
        customProgressDialog.setOnKeyListener(new OnDismissKeyListener(customProgressDialog));
        if (isShowDialog) {
            customProgressDialog.show();
        }
        return customProgressDialog;
    }

    public static void dismissDialog() {

        if (null != customProgressDialog) {
            customProgressDialog.dismiss();
        }

    }

//    public static Dialog getDialog(Context context, boolean isShowDialog) {
//        Dialog dialog = new Dialog(context, R.style.RequestLoadingDialogStyle);
//        dialog.setContentView(R.layout.request_loding_dialog_view);
//        dialog.setCancelable(false);
//        dialog.setOnKeyListener(new OnDismissKeyListener(dialog));
//        if (isShowDialog && (!dialog.isShowing())) {
//            dialog.show();
//        }
//        return dialog;
//    }

    private static class OnDismissKeyListener implements DialogInterface.OnKeyListener {

        private Dialog dialog;

        public OnDismissKeyListener(Dialog dialog) {
            super();
            this.dialog = dialog;
        }

        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

            if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (this.dialog != null && this.dialog.isShowing()) {
                    this.dialog.dismiss();
                }
            }
            return false;
        }

    }


}