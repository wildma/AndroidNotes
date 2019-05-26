package com.wildma.androidnotes.designpattern.singleton.singleton4;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${单例模式-双重校验锁（线程安全）}
 */
public class Singleton {
    private volatile static Singleton instance;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
