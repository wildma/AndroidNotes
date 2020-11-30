package com.wildma.androidnotes.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.wildma.androidnotes.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${EventBusFirstActivity}
 */
public class EventBusFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_first);
        // 注册（普通事件）
        EventBus.getDefault().register(this);
    }

    public void jumpToEventBusSecondActivity(View view) {
        // 发送粘性事件（粘性事件）
        EventBus.getDefault().postSticky(new StickyMessageEvent("粘性事件"));

        startActivity(new Intent(this, EventBusSecondActivity.class));
    }

    /**
     * 准备订阅者（普通事件）
     *
     * @param event 事件实体类
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除注册（普通事件）
        EventBus.getDefault().unregister(this);
    }
}