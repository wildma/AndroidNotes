package com.wildma.androidnotes.designpattern.builder;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${产品类}
 */
public class Product {

    private String engine;//发动机
    private String tire;//轮胎
    private String transmission;//变速器

    //展示产品
    public void showProduct() {
        System.out.println(new StringBuffer().append(getEngine()).append(",")
                .append(getTire()).append(",").append(getTransmission()).toString());
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTire() {
        return tire;
    }

    public void setTire(String tire) {
        this.tire = tire;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
