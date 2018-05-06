package com.ccpunion.comrade.login.bean;

/**
 * Created by Administrator on 2018/5/5.
 */

public class EveryIsAnswerBean {

    /**
     * code : 0
     * msg : 操作成功！
     * body : {"exist":0}
     */

    private String code;
    private String msg;
    private BodyBean body;

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

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * exist : 0
         */

        private int exist;

        public int getExist() {
            return exist;
        }

        public void setExist(int exist) {
            this.exist = exist;
        }
    }
}
