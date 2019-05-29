package com.wildma.androidnotes.designpattern.observer;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体被观察者-优惠商品}
 */
public class DiscountedGoods extends Subject {

    //活动开始
    public void activityBegin() {
        System.out.println("活动开始啦！");
        super.notifyObservers();//活动开始，通知观察者
    }
}