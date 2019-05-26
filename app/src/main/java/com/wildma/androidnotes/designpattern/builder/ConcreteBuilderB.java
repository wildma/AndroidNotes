package com.wildma.androidnotes.designpattern.builder;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体建造者-宝马 X5}
 */
public class ConcreteBuilderB extends Builder {

    private Product product = new Product();

    @Override
    public void buildEngine() {
        product.setEngine("装A2型号的发动机");
    }

    @Override
    public void buildTire() {
        product.setTire("装A2型号的轮胎");
    }

    @Override
    public void buildTransmission() {
        product.setTransmission("装A2型号的变速器");
    }

    @Override
    public Product getProduct() {
        return product;
    }

}
