package com.ccpunion.comrade.login.bean;

/**
 * Created by Administrator on 2018/5/6.
 */

public class LoginBean {


    /**
     * code : 0
     * msg : 登录成功！
     * body : {"communistId":3,"account":"17786509688","name":"潜海龙","headImage":"http://118.31.76.211:8080/upload/image/1517382865084/tmp1517382863111.jpeg","orgName":"中共黄石IT客户端研发支部","orgId":200000669,"orgChainId":"10000000#200000000#200000668#200000669","phone":"17786509688"}
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
         * communistId : 3
         * account : 17786509688
         * name : 潜海龙
         * headImage : http://118.31.76.211:8080/upload/image/1517382865084/tmp1517382863111.jpeg
         * orgName : 中共黄石IT客户端研发支部
         * orgId : 200000669
         * orgChainId : 10000000#200000000#200000668#200000669
         * phone : 17786509688
         */

        private int communistId;
        private String account;
        private String name;
        private String headImage;
        private String orgName;
        private int orgId;
        private String orgChainId;
        private String phone;

        public int getCommunistId() {
            return communistId;
        }

        public void setCommunistId(int communistId) {
            this.communistId = communistId;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public int getOrgId() {
            return orgId;
        }

        public void setOrgId(int orgId) {
            this.orgId = orgId;
        }

        public String getOrgChainId() {
            return orgChainId;
        }

        public void setOrgChainId(String orgChainId) {
            this.orgChainId = orgChainId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
