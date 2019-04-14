package com.wildma.androidnotes.thread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wildma.androidnotes.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Author       wildma
 * Date         2017/10/6
 * Desc	        ${四种线程池的使用demo}
 */
public class ThreadPoolActivity extends AppCompatActivity {

    private final String          TAG = this.getClass().getSimpleName();
    private       ExecutorService mNewFixedThreadPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool);
        getSupportActionBar().setTitle("四种线程池的使用");

        newFixedThreadPool();
        //        newSingleThreadExecutor();
        //        newCachedThreadPool();
        //        newScheduledThreadPool();

    }

    /**
     * 创建固定大小的线程池
     */
    private void newFixedThreadPool() {
        mNewFixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            mNewFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        Log.d(TAG, "线程名字:" + Thread.currentThread().getName() + "  i=" + finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 创建单线程的线程池
     */
    private void newSingleThreadExecutor() {
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            newSingleThreadExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        Log.d(TAG, "线程名字:" + Thread.currentThread().getName() + "  i=" + finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 创建带有缓存的线程池
     */
    private void newCachedThreadPool() {
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int finalI = i;
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "线程名字:" + Thread.currentThread().getName() + "  i=" + finalI);
                }
            });
        }
    }

    /**
     * 创建定时和周期性执行的线程池
     */
    private void newScheduledThreadPool() {
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(3);
        /**
         * 延迟2秒执行任务
         */
        newScheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "线程名字:" + Thread.currentThread().getName() + "定时任务");
            }
        }, 2, TimeUnit.SECONDS);

        /**
         * 延迟1秒后每2秒执行一次任务
         */
        newScheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "线程名字:" + Thread.currentThread().getName() + "周期性任务");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //页面销毁的时候关闭线程池
        if (mNewFixedThreadPool != null && !mNewFixedThreadPool.isShutdown()) {
            mNewFixedThreadPool.shutdownNow();//关闭线程池
        }
    }
}
