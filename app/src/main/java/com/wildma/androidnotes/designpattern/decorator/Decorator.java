package com.wildma.androidnotes.designpattern.decorator;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${抽象装饰者}
 */
public class Decorator implements Actor {

    private Actor mActor = null;

    //通过构造函数传递被修饰者
    public Decorator(Actor actor) {
        this.mActor = actor;
    }

    //委托给被修饰者执行
    @Override
    public void appearance() {
        mActor.appearance();
    }
}
