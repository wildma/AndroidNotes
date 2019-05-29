package com.wildma.androidnotes.designpattern.observer;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体观察者-买家}
 */
public class Buyer implements Observer {

    public void update() {
        System.out.println("我要开始买啦！");
    }
}
