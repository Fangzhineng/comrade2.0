package com.ccpunion.comrade.page.concentric.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/24.
 */

public class TestBean {
    /**
     * code : 0
     * msg : 操作成功！
     * body : [{"uid":"1000","sex":"男","name":"潜海龙","headImages":"http://172.16.9.37:8080/upload/banner/manager/manager1.png"},{"uid":"1001","sex":"男","name":"方智能","headImages":"http://172.16.9.37:8080/upload/banner/manager/manager2.png"},{"uid":"1002","sex":"男","name":"李诚","headImages":"http://172.16.9.37:8080/upload/banner/manager/manager3.png"}]
     */

    private String code;
    private String msg;
    private List<BodyBean> body;

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

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        /**
         * uid : 1000
         * sex : 男
         * name : 潜海龙
         * headImages : http://172.16.9.37:8080/upload/banner/manager/manager1.png
         */

        private String uid;
        private String sex;
        private String name;
        private String headImages;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadImages() {
            return headImages;
        }

        public void setHeadImages(String headImages) {
            this.headImages = headImages;
        }
    }
}
