package com.wildma.androidnotes.designpattern.decorator;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体装饰者-画上白胡须}
 */
public class WhiteBeardDecorator extends Decorator {

    //定义被修饰者
    public WhiteBeardDecorator(Actor actor) {
        super(actor);
    }

    //定义自己的修饰方法（化妆）
    private void makeup() {
        System.out.println("白胡须");
    }

    //重写父类的方法
    @Override
    public void appearance() {
        this.makeup();
        super.appearance();
    }
}
