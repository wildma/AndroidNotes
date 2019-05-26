package com.wildma.androidnotes.designpattern.builder;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${建造者模式客户端使用}
 */
public class Client {

    public static void main(String[] args) {

        /*生产宝马 X3*/
        Builder builder = new ConcreteBuilderA();
        Director director = new Director(builder);
        director.construct();
        builder.getProduct().showProduct();

        /*生产宝马 X5*/
        builder = new ConcreteBuilderB();
        director = new Director(builder);
        director.construct();
        builder.getProduct().showProduct();

    }
}
