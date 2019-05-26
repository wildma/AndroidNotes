package com.wildma.androidnotes.designpattern.decorator;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${装饰模式客户端使用}
 */
public class Client {

    public static void main(String[] args) {
        //没化妆的小明
        Actor component = new ActorXiaoMing();
        //给小明画了白头发
        component = new WhiteHairDecorator(component);
        //给小明画了白胡须
        component = new WhiteBeardDecorator(component);
        //化完妆，要出场了
        component.appearance();
    }
}
