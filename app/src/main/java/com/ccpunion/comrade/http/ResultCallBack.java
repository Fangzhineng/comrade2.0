package com.ccpunion.comrade.http;

import java.io.IOException;

import okhttp3.Request;


public interface ResultCallBack {

    /***
     * 从服务器获取数据成功
     *
     * @param response 返回内容
     * @param flag     标识
     */
    public void onSuccess(String response, int flag);


    /***
     * 从服务器获取数据失败
     *
     * @param request
     * @param e
     * @param flag    标识
     */
    public void onFailure(Request request, IOException e, int flag);
}
