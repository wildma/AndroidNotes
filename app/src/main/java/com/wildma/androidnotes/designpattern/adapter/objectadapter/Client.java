package com.wildma.androidnotes.designpattern.adapter.objectadapter;


/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${类适配器客户端使用}
 */
public class Client {

    public static void main(String[] args) {

        MobilePhone mobilePhone = new MobilePhone();
        Headset headset = new Adapter(mobilePhone);

        //可以看到，表面是耳机插入了 3.5 耳机口
        //但是 headset_3_5_port() 方法内部调用的是 Phone 的 typeCPort() 方法
        //所以通过适配器，耳机实际插入了 Type-C 端口
        headset.headset_3_5_port();
    }
}
