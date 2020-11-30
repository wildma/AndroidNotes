package com.wildma.androidnotes.eventbus;

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
 * Desc	        ${EventBusSecondActivity}
 */
public class EventBusSecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_second);
        // 注册（粘性事件）
        EventBus.getDefault().register(this);
    }

    public void sendEvent(View view) {
        // 发送事件（普通事件）
        EventBus.getDefault().post(new MessageEvent("普通事件"));
        finish();
    }

    /**
     * 准备订阅者（粘性事件）
     *
     * @param event 事件实体类
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(StickyMessageEvent event) {
        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除注册（粘性事件）
        EventBus.getDefault().unregister(this);
    }
}