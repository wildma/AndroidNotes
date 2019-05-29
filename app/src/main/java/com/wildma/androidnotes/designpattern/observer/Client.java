package com.wildma.androidnotes.designpattern.observer;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${观察者模式客户端使用}
 */
public class Client {

    public static void main(String[] args) {
        //创建被观察者
        DiscountedGoods discountedGoods = new DiscountedGoods();
        //创建观察者
        Observer observer = new Buyer();
        //观察者观察被观察者
        discountedGoods.addObserver(observer);
        //观察者开始某个业务了
        discountedGoods.activityBegin();
    }
}
