package com.wildma.androidnotes.designpattern.templatemethod;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${实现类-洗羊毛衫}
 */
public class WashSweater extends WashingMachine {

    @Override
    void wash() {
        System.out.println("洗羊毛衫-洗涤");
    }

    @Override
    void rinse() {
        System.out.println("洗羊毛衫-漂洗");
    }

    @Override
    void dehydrate() {
        System.out.println("洗羊毛衫-脱水");
    }
}
