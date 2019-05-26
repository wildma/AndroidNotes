package com.wildma.androidnotes.designpattern.proxy.dynamicproxy;

import com.wildma.androidnotes.designpattern.proxy.RealSubject;
import com.wildma.androidnotes.designpattern.proxy.Subject;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${动态代理客户端使用}
 */
public class Client {

    public static void main(String[] args) {
        DynamicProxy DynamicProxy = new DynamicProxy();
        RealSubject realSubject = new RealSubject();
        Subject subject = (Subject) DynamicProxy.newProxyInstance(realSubject);
        //这里会先调用 DynamicProxy 类中的 invoke() 方法，然后再通过该方法中的反射机制来调用被代理对象（RealSubject）的 buy() 方法
        subject.buy();
    }
}
