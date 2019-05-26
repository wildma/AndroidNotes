package com.wildma.androidnotes.designpattern.proxy;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${代理模式客户端使用}
 */
public class Client {

    public static void main(String[] args) {
        Subject proxy = new Proxy();
        proxy.buy();
    }
}
