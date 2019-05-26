package com.wildma.androidnotes.designpattern.factory;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${抽象工厂}
 */
public abstract class Factory {
    public abstract <T extends Product> T createProduct(Class<T> clz);
}
