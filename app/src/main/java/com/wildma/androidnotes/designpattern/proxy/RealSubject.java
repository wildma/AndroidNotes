package com.wildma.androidnotes.designpattern.proxy;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体对象类}
 */
public class RealSubject implements Subject {

    @Override
    public void buy() {
        System.out.println("购买港版 iPhone");
    }
}
