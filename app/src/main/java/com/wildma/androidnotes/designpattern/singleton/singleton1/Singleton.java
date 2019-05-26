package com.wildma.androidnotes.designpattern.singleton.singleton1;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${单例模式-饿汉式（线程安全）}
 */
public class Singleton {
    private static Singleton instance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }
}
