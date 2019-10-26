package com.wildma.androidnotes;

import android.content.Intent;
import android.view.View;

import com.wildma.androidnotes.base.BaseActivity;
import com.wildma.androidnotes.http.HttpActivity;
import com.wildma.androidnotes.okhttp.OkHttpActivity;
import com.wildma.androidnotes.retrofit.RetrofitActivity;
import com.wildma.androidnotes.thread.AboutThreadActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    protected void initPresenter() {
    }

    public void jumpToAboutThreadActivity(View view) {
        startActivity(new Intent(this, AboutThreadActivity.class));
    }

    public void jumpToHttpActivity(View view) {
        startActivity(new Intent(this, HttpActivity.class));
    }

    public void jumpToOkHttpActivity(View view) {
        startActivity(new Intent(this, OkHttpActivity.class));
    }

    public void jumpToRetrofitActivity(View view) {
        startActivity(new Intent(this, RetrofitActivity.class));
    }

}
