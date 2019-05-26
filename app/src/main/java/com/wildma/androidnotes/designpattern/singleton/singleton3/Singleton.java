package com.wildma.androidnotes.designpattern.singleton.singleton3;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${单例模式-懒汉式（线程安全）}
 */
public class Singleton {
    private static Singleton instance;

    private Singleton() {
    }

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
