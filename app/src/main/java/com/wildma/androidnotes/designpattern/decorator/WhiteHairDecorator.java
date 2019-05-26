package com.wildma.androidnotes.designpattern.decorator;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体装饰者-画上白头发}
 */
public class WhiteHairDecorator extends Decorator {

    //定义被修饰者
    public WhiteHairDecorator(Actor actor) {
        super(actor);
    }

    //定义自己的修饰方法（化妆）
    private void makeup() {
        System.out.println("白头发");
    }

    //重写父类的方法
    @Override
    public void appearance() {
        this.makeup();
        super.appearance();
    }
}
