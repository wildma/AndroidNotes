package com.wildma.androidnotes.retrofit;

import com.google.gson.annotations.SerializedName;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${PostmanGetBean}
 */
public class PostmanGetBean {

    @SerializedName("args")
    private ArgsEntity    args;
    private HeadersEntity headers;
    private String        url;

    public ArgsEntity getArgs() {
        return args;
    }

    public void setArgs(ArgsEntity args) {
        this.args = args;
    }

    public HeadersEntity getHeaders() {
        return headers;
    }

    public void setHeaders(HeadersEntity headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ArgsEntity {
    }

    public static class HeadersEntity {
        @SerializedName("x-forwarded-proto")
        private String xforwardedproto;
        private String host;
        private String accept;
        @SerializedName("accept-encoding")
        private String acceptencoding;
        @SerializedName("accept-language")
        private String acceptlanguage;
        @SerializedName("cache-control")
        private String cachecontrol;
        private String cookie;
        @SerializedName("postman-token")
        private String postmantoken;
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

        public String getAccept() {
            return accept;
        }

        public void setAccept(String accept) {
            this.accept = accept;
        }

        public String getAcceptencoding() {
            return acceptencoding;
        }

        public void setAcceptencoding(String acceptencoding) {
            this.acceptencoding = acceptencoding;
        }

        public String getAcceptlanguage() {
            return acceptlanguage;
        }

        public void setAcceptlanguage(String acceptlanguage) {
            this.acceptlanguage = acceptlanguage;
        }

        public String getCachecontrol() {
            return cachecontrol;
        }

        public void setCachecontrol(String cachecontrol) {
            this.cachecontrol = cachecontrol;
        }

        public String getCookie() {
            return cookie;
        }

        public void setCookie(String cookie) {
            this.cookie = cookie;
        }

        public String getPostmantoken() {
            return postmantoken;
        }

        public void setPostmantoken(String postmantoken) {
            this.postmantoken = postmantoken;
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
}
