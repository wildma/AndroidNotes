package com.wildma.androidnotes.designpattern.templatemethod;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${抽象类-洗衣机}
 */
public abstract class WashingMachine {

    //1. 洗涤：标准洗与洗羊毛衫力度与速度不一样，由子类去实现。
    abstract void wash();

    //2. 漂洗：标准洗与洗羊毛衫力度与速度不一样，由子类去实现。
    abstract void rinse();

    //3. 脱水：标准洗与洗羊毛衫力度与速度不一样，由子类去实现。
    abstract void dehydrate();

    //模板方法——洗衣服：标准洗与洗羊毛衫的步骤都是一样的，直接在这里实现。
    final void washClothes() {
        wash();
        rinse();
        dehydrate();
    }
}
