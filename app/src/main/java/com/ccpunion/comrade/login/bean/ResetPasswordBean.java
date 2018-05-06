package com.ccpunion.comrade.login.bean;

/**
 * Created by Administrator on 2018/5/6.
 */

public class ResetPasswordBean {


    /**
     * code : 0
     * msg : 操作成功！
     * body : null
     */

    private String code;
    private String msg;
    private Object body;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
