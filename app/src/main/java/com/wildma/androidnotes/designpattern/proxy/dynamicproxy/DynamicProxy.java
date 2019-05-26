package com.wildma.androidnotes.designpattern.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${动态代理类}
 */
public class DynamicProxy implements InvocationHandler {

    private Object mObject;

    /**
     * 生成动态代理对象
     *
     * @param object 被代理对象
     */
    public Object newProxyInstance(Object object) {

        this.mObject = object;
        //获得 ClassLoader
        ClassLoader classLoader = object.getClass().getClassLoader();
        //获得接口数组
        Class<?>[] interfaces = object.getClass().getInterfaces();
        //生成动态代理对象
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),
                object.getClass().getInterfaces(), this);
    }

    /**
     * InvocationHandler 接口需要重写的方法（这里会调用被代理的方法）
     *
     * @param proxy  动态代理对象
     * @param method 被代理对象的方法
     * @param args   指定被调用方法的参数
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //通过Java反射机制调用被代理对象的方法
        Object result = method.invoke(this.mObject, args);
        //邮寄
        post();
        return result;
    }

    public void post() {
        System.out.println("将买好的东西邮寄给买家");
    }
}
