package com.ccpunion.comrade.http;


public class ErrMsg {

    private ErrInfo err;

    public ErrMsg() {

    }

    public ErrInfo getErr() {
        return err;
    }


    public void setErr(ErrInfo err) {
        this.err = err;
    }

    public class ErrInfo {

        public String errMessage;
        public String errCode;

        @Override
        public String toString() {
            return "LoginInfo [errMessage=" + errMessage + ", errCode=" + errCode
                    + "]";
        }

        public ErrInfo() {

        }

        public String getErrMessage() {
            return errMessage;
        }

        public void setErrMessage(String errMessage) {
            this.errMessage = errMessage;
        }

        public String getErrCode() {
            return errCode;
        }

        public void setErrCode(String errCode) {
            this.errCode = errCode;
        }
    }

}
