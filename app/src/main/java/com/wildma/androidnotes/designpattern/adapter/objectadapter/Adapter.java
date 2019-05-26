package com.wildma.androidnotes.designpattern.adapter.objectadapter;


/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${适配器角色}
 */
class Adapter implements Headset {

    private MobilePhone mAdaptee;

    //通过构造函数传入需要适配的类
    public Adapter(MobilePhone adaptee) {
        this.mAdaptee = adaptee;
    }

    //headset_3_5_port() 方法中调用了 typeCPort() 方法，也就实现了将 Type-C 端口转成 3.5 耳机口。
    @Override
    public void headset_3_5_port() {
        this.mAdaptee.typeCPort();
    }
}
