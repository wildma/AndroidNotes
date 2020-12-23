package com.wildma.androidnotes.designpattern.singleton.singletonkotlin3

import android.content.Context


/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${单例模式-双重校验锁（线程安全、带参数）}
 */
class Singleton private constructor(context: Context) {

    companion object {
        @Volatile
        private var instance: Singleton? = null

        fun getInstance(context: Context) =
                instance ?: synchronized(this) {
                    instance ?: Singleton(context).also { instance = it }
                }
    }
}



