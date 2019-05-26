package com.wildma.androidnotes.designpattern.builder;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${抽象建造者}
 */
public abstract class Builder {

    //装发动机，具体由子类实现
    public abstract void buildEngine();

    //装轮胎，具体由子类实现
    public abstract void buildTire();

    //装变速器，具体由子类实现
    public abstract void buildTransmission();

    //获取产品
    public abstract Product getProduct();

}
