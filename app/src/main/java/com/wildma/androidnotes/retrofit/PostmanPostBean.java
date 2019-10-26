package com.wildma.androidnotes.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${PostmanPostBean}
 */
public class PostmanPostBean {

    /**
     * args : {}
     * data :
     * files : {}
     * form : {"username":"wildma","password":"123456"}
     * headers : {"x-forwarded-proto":"https","host":"postman-echo.com","content-length":"31","content-type":"application/x-www-form-urlencoded","user-agent":"Apache-HttpClient/UNAVAILABLE (java 1.4)","x-forwarded-port":"443"}
     * json : {"username":"wildma","password":"123456"}
     * url : https://postman-echo.com/post
     */

    @SerializedName("args")
    private ArgsEntity    args;
    private String        data;
    private FilesEntity   files;
    private FormEntity    form;
    private HeadersEntity headers;
    private JsonEntity    json;
    private String        url;

    public ArgsEntity getArgs() {
        return args;
    }

    public void setArgs(ArgsEntity args) {
        this.args = args;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public FilesEntity getFiles() {
        return files;
    }

    public void setFiles(FilesEntity files) {
        this.files = files;
    }

    public FormEntity getForm() {
        return form;
    }

    public void setForm(FormEntity form) {
        this.form = form;
    }

    public HeadersEntity getHeaders() {
        return headers;
    }

    public void setHeaders(HeadersEntity headers) {
        this.headers = headers;
    }

    public JsonEntity getJson() {
        return json;
    }

    public void setJson(JsonEntity json) {
        this.json = json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ArgsEntity {
    }

    public static class FilesEntity {
    }

    public static class FormEntity {
        /**
         * username : wildma
         * password : 123456
         */

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @Override
        public String toString() {
            return "FormEntity{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }

    public static class HeadersEntity {
        /**
         * x-forwarded-proto : https
         * host : postman-echo.com
         * content-length : 31
         * content-type : application/x-www-form-urlencoded
         * user-agent : Apache-HttpClient/UNAVAILABLE (java 1.4)
         * x-forwarded-port : 443
         */

        @SerializedName("x-forwarded-proto")
        private String xforwardedproto;
        private String host;
        @SerializedName("content-length")
        private String contentlength;
        @SerializedName("content-type")
        private String contenttype;
        @SerializedName("user-agent")
        private String useragent;
        @SerializedName("x-forwarded-port")
        private String xforwardedport;

        public String getXforwardedproto() {
            return xforwardedproto;
        }

        public void setXforwardedproto(String xforwardedproto) {
            this.xforwardedproto = xforwardedproto;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getContentlength() {
            return contentlength;
        }

        public void setContentlength(String contentlength) {
            this.contentlength = contentlength;
        }

        public String getContenttype() {
            return contenttype;
        }

        public void setContenttype(String contenttype) {
            this.contenttype = contenttype;
        }

        public String getUseragent() {
            return useragent;
        }

        public void setUseragent(String useragent) {
            this.useragent = useragent;
        }

        public String getXforwardedport() {
            return xforwardedport;
        }

        public void setXforwardedport(String xforwardedport) {
            this.xforwardedport = xforwardedport;
        }
    }

    public static class JsonEntity {
        /**
         * username : wildma
         * password : 123456
         */

        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
