package com.wildma.androidnotes.designpattern.adapter.classadapter;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${适配器角色}
 */
class Adapter extends MobilePhone implements Headset {

    //headset_3_5_port() 方法中调用了 typeCPort() 方法，也就实现了将 Type-C 端口转成 3.5 耳机口。
    @Override
    public void headset_3_5_port() {
        this.typeCPort();
    }
}
