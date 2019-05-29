package com.wildma.androidnotes.designpattern.observer.java;


import java.util.Observable;
import java.util.Observer;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体观察者-买家}
 */
public class Buyer implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("我要开始买啦！");
    }
}
