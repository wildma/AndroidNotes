package com.wildma.androidnotes.designpattern.abstractfactory;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体工厂 2}
 */
public class ConcreteFactory2 extends Factory {
    @Override
    public IProductA createProductA() {
        return new ConcreteProductA2();
    }

    @Override
    public IProductB createProductB() {
        return new ConcreteProductB2();
    }
}
