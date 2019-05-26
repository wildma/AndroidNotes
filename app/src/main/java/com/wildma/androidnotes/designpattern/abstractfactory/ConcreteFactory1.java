package com.wildma.androidnotes.designpattern.abstractfactory;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体工厂 1}
 */
public class ConcreteFactory1 extends Factory {
    @Override
    public IProductA createProductA() {
        return new ConcreteProductA1();
    }

    @Override
    public IProductB createProductB() {
        return new ConcreteProductB1();
    }
}
