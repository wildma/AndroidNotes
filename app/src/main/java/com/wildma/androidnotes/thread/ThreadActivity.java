package com.wildma.androidnotes.thread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.wildma.androidnotes.R;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Author       wildma
 * Date         2017/10/5
 * Desc	        ${创建线程的三种方式demo}
 */
public class ThreadActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        getSupportActionBar().setTitle("创建线程的三种方式");

        myThread();
        //        myRunnable();
        //        myCallable();
    }

    /**
     * 继承Thread的方式创建线程
     */
    private void myThread() {
        //3、创建该类的实例，并调用start()方法开启线程。
        MyThread myThread = new MyThread();
        myThread.start();
    }

    /**
     * 实现Runnable的方式创建线程
     */
    private void myRunnable() {
        //3、创建Thread对象, 传入MyRunnable的实例，并调用start()方法开启线程。
        Thread thread = new Thread(new MyRunnable());
        thread.start();
    }

    /**
     * 实现Callable的方式创建线程
     */
    private void myCallable() {
        //3、创建线程池对象，调用submit()方法执行MyCallable任务，并返回Future对象
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Future<Integer> f1 = pool.submit(new MyCallable());

        //4、调用Future对象的get()方法获取call()方法执行完后的值
        try {
            Log.d(TAG, "sum=" + f1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //关闭线程池
        pool.shutdown();
    }


    //1、定义一个类MyThread继承Thread，并重写run方法。
    class MyThread extends Thread {
        public void run() {
            //2、将执行的代码写在run方法中。
            for (int i = 0; i < 100; i++) {
                Log.d(TAG, "线程名字:" + getName() + "  i=" + i);
            }
        }
    }

    //1、定义一个类MyRunnable实现Runnable，并重写run方法。
    class MyRunnable implements Runnable {
        public void run() {
            //2、将执行的代码写在run方法中。
            for (int i = 0; i < 100; i++) {
                Log.d(TAG, "线程名字:" + Thread.currentThread().getName() + "  i=" + i);
            }
        }
    }

    //1、自定义一个类MyCallable实现Callable接口，并重写call()方法
    public class MyCallable implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            //2、将要执行的代码写在call()方法中
            int sum = 0;
            for (int i = 0; i <= 100; i++) {
                sum += i;
            }
            return sum;
        }

    }

}
