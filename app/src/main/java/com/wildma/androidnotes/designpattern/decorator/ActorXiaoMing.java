package com.wildma.androidnotes.designpattern.decorator;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${具体构件-演员小明}
 */
public class ActorXiaoMing implements Actor {

    @Override
    public void appearance() {
        System.out.println("出场了");
    }
}
