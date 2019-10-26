package com.wildma.androidnotes.retrofit;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${UploadImgBean}
 */
public class UploadImgBean {

    /**
     * ret : 200
     * data : {"err_code":0,"err_msg":"","url":""}
     * msg :
     */

    private int        ret;
    private DataEntity data;
    private String     msg;

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataEntity {
        /**
         * err_code : 0
         * err_msg :
         * url :
         */

        private int    err_code;
        private String err_msg;
        private String url;

        public int getErr_code() {
            return err_code;
        }

        public void setErr_code(int err_code) {
            this.err_code = err_code;
        }

        public String getErr_msg() {
            return err_msg;
        }

        public void setErr_msg(String err_msg) {
            this.err_msg = err_msg;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "DataEntity{" +
                    "err_code=" + err_code +
                    ", err_msg='" + err_msg + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UploadImgBean{" +
                "ret=" + ret +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
