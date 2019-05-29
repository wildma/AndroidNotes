package com.wildma.androidnotes.designpattern.observer.java;


import java.util.Observable;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体被观察者-优惠商品}
 */
public class DiscountedGoods extends Observable {

    //活动开始
    public void activityBegin() {
        System.out.println("活动开始啦！");
        super.setChanged();//标记被观察者已经改变
        super.notifyObservers();//活动开始，通知观察者
    }
}