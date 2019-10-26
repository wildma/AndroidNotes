package com.wildma.androidnotes.retrofit;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${TestBodyBean}
 */
public class TestBodyBean {

    private String username;
    private String password;

    public String getUsername() {
        return username == null ? "" : username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TestBodyBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
