package com.wildma.androidnotes.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


/**
 * Author   wildma
 * Date     2019/04/16
 * Desc     ${Activity基类}
 */
public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initPresenter();
    }

    /**
     * 初始化布局
     */
    protected abstract int initLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化业务逻辑
     */
    protected abstract void initPresenter();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTitle(@StringRes int resId) {
        getSupportActionBar().setTitle(resId);
    }

    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
