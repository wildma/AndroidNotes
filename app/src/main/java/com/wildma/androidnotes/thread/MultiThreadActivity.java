package com.wildma.androidnotes.thread;

import android.util.Log;
import android.view.View;

import com.wildma.androidnotes.R;
import com.wildma.androidnotes.base.BaseActivity;


/**
 * Author       wildma
 * Date         2017/10/5
 * Desc	        ${多线程并发1}
 */
public class MultiThreadActivity  extends BaseActivity {

    private final String TAG = this.getClass().getSimpleName();
    //图片数量
    private       int    mImgNum;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_multi_thread;
    }

    @Override
    protected void initView() {
        setTitle("多线程并发1");
    }

    @Override
    protected void initPresenter() {

    }

    public void multiThread(View view) {
        mImgNum = 9;
        //开启3条线程上传图片
        MyRunnable myRunnable = new MyRunnable();
        new Thread(myRunnable).start();
        new Thread(myRunnable).start();
        new Thread(myRunnable).start();
    }

    public class MyRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                //加上synchronized进行同步，保证在同一时刻只能有一个线程能够访问
                synchronized (MyRunnable.class) {
                    if (mImgNum == 0) {
                        Log.d(TAG, Thread.currentThread().getName() + "全部上传成功");
                        break;
                    }
                    try {
                        //模拟上传一张图片需要1秒钟的时间
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mImgNum--;
                    Log.d(TAG, Thread.currentThread().getName() + "上传了一张图片...，还剩" + mImgNum + "张图片未上传");
                }
            }
        }
    }
}
