package com.wildma.androidnotes.designpattern.factory;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${工厂模式客户端使用}
 */
public class Client {

    public static void main(String[] args) {

        Factory factory = new ConcreteFactory();

        //创建产品 A
        Product productA = factory.createProduct(ConcreteProductA.class);
        productA.method();

        //创建产品 B
        Product productB = factory.createProduct(ConcreteProductB.class);
        productB.method();
    }
}
