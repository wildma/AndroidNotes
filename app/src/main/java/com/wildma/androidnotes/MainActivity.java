package com.wildma.androidnotes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wildma.androidnotes.thread.ThreadActivity;
import com.wildma.androidnotes.thread.ThreadPoolActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jumpToThreadActivity(View view) {
        startActivity(new Intent(this, ThreadActivity.class));
    }

    public void jumpToThreadPoolActivity(View view) {
        startActivity(new Intent(this, ThreadPoolActivity.class));
    }
}
