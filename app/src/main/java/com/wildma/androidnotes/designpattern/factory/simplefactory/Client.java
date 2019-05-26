package com.wildma.androidnotes.designpattern.factory.simplefactory;

import com.wildma.androidnotes.designpattern.factory.ConcreteProductA;
import com.wildma.androidnotes.designpattern.factory.ConcreteProductB;
import com.wildma.androidnotes.designpattern.factory.Product;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${简单工厂客户端使用}
 */
public class Client {

    public static void main(String[] args) {

        //创建产品 A
        Product productA = ConcreteFactory.createProduct(ConcreteProductA.class);
        productA.method();

        //创建产品 B
        Product productB = ConcreteFactory.createProduct(ConcreteProductB.class);
        productB.method();
    }
}
