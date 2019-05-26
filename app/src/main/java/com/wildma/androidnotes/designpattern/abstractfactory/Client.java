package com.wildma.androidnotes.designpattern.abstractfactory;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${抽象工厂模式客户端使用}
 */
public class Client {

    public static void main(String[] args) {

        Factory concreteFactory1 = new ConcreteFactory1();
        Factory concreteFactory2 = new ConcreteFactory2();
        IProductA productA1 = concreteFactory1.createProductA();
        IProductA productA2 = concreteFactory2.createProductA();
        IProductB productB1 = concreteFactory1.createProductB();
        IProductB productB2 = concreteFactory2.createProductB();

        productA1.method();
        productA2.method();
        productB1.method();
        productB2.method();
    }
}
