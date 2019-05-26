package com.wildma.androidnotes.designpattern.templatemethod;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${模板方法模式客户端使用}
 */
public class Client {

    public static void main(String[] args) {

        /*标准洗*/
        WashingMachine standardWashing = new StandardWashing();
        standardWashing.washClothes();

        /*洗羊毛衫*/
        WashingMachine washSweater = new WashSweater();
        washSweater.washClothes();
    }
}
