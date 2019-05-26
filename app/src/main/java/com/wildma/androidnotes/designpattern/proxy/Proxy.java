package com.wildma.androidnotes.designpattern.proxy;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${代理对象类}
 */
public class Proxy implements Subject {

    @Override
    public void buy() {
        //创建被代理的对象
        RealSubject realSubject = new RealSubject();
        //购买东西
        realSubject.buy();
        //邮寄
        post();
    }

    public void post() {
        System.out.println("将买好的东西邮寄给买家");
    }
}
