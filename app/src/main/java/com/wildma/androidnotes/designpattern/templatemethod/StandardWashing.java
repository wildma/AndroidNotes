package com.wildma.androidnotes.designpattern.templatemethod;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${实现类-标准洗}
 */
public class StandardWashing extends WashingMachine {

    @Override
    void wash() {
        System.out.println("标准洗-洗涤");
    }

    @Override
    void rinse() {
        System.out.println("标准洗-漂洗");
    }

    @Override
    void dehydrate() {
        System.out.println("标准洗-脱水");
    }
}
