package com.wildma.androidnotes.designpattern.singleton.singleton5;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${单例模式-静态内部类（线程安全）}
 */
public class Singleton {
    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }

    private Singleton() {
    }

    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
