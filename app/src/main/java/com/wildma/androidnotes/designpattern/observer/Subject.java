package com.wildma.androidnotes.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${抽象被观察者}
 */
public abstract class Subject {

    //定义一个观察者数组
    private List<Observer> list = new ArrayList<Observer>();

    //增加一个观察者
    public void addObserver(Observer o) {
        this.list.add(o);
    }

    //删除一个观察者
    public void delObserver(Observer o) {
        this.list.remove(o);
    }

    //通知所有观察者
    public void notifyObservers() {
        for (Observer observer : list) {
            observer.update();
        }
    }
}
