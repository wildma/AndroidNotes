package com.wildma.androidnotes.thread;

import android.content.Intent;
import android.view.View;

import com.wildma.androidnotes.R;
import com.wildma.androidnotes.base.BaseActivity;

/**
 * Author       wildma
 * Date         2019/04/16
 * Desc	        ${线程相关}
 */
public class AboutThreadActivity extends BaseActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_about_thread;
    }

    @Override
    protected void initView() {
        setTitle("线程相关");
    }

    @Override
    protected void initPresenter() {
    }

    public void jumpToThreadActivity(View view) {
        startActivity(new Intent(this, ThreadActivity.class));
    }

    public void jumpToMultiThreadActivity(View view) {
        startActivity(new Intent(this, MultiThreadActivity.class));
    }

    public void jumpToMultiThread2Activity(View view) {
        startActivity(new Intent(this, MultiThread2Activity.class));
    }

    public void jumpToThreadPoolActivity(View view) {
        startActivity(new Intent(this, ThreadPoolActivity.class));
    }

}
