package com.wildma.androidnotes.okhttp;

import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.wildma.androidnotes.R;
import com.wildma.androidnotes.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Date         2019/8/17
 * Desc	        ${OkHttp 的使用}
 */
public class OkHttpActivity extends BaseActivity {

    private Call mCall;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_okhttp;
    }

    @Override
    protected void initView() {
        setTitle("OkHttp 的使用");
    }

    @Override
    protected void initPresenter() {

    }

    public void syncGetRequestByOkHttp(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    syncGetRequestByOkHttp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void asyncGetRequestByOkHttp(View view) {
        asyncGetRequestByOkHttp();
    }

    public void asyncPostRequestByOkHttp(View view) {
        asyncPostRequestByOkHttp();
    }

    public void asyncPostFileByOkHttp(View view) {
        asyncPostFileByOkHttp();
    }

    public void asyncPostMultipartRequestByOkHttp(View view) {
        asyncPostMultipartRequestByOkHttp();
    }

    public void asyncGetRequestByOkHttpAndSetTimeout(View view) {
        asyncGetRequestByOkHttpAndSetTimeout();
    }

    public void asyncGetRequestByOkHttpAndCancelRequest(View view) {
        asyncGetRequestByOkHttpAndCancelRequest();
    }

    /**
     * 同步 GET 请求
     */
    private void syncGetRequestByOkHttp() throws Exception {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            Log.i(TAG, "syncGetRequestByOkHttp data-->" + response.body().string());
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    /**
     * 异步 GET 请求
     */
    private void asyncGetRequestByOkHttp() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse data-->" + response.body().string());
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }

    /**
     * 异步 POST 请求
     */
    private void asyncPostRequestByOkHttp() {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("username", "wildma")
                .add("password", "123456")
                .build();
        Request request = new Request.Builder()
                .url("https://postman-echo.com/post")
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse data-->" + response.body().string());
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }

    /**
     * 异步 POST 方式上传文件
     */
    private void asyncPostFileByOkHttp() {
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        //test.txt 为 SD 卡跟目录下的文件，需要提前放好
        File file = new File(Environment.getExternalStorageDirectory(), "test.txt");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_MARKDOWN, file);
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure IOException-->" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse data-->" + response.body().string());
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }

    /**
     * 异步 POST multipart 请求
     * （由于这里没有合适的测试服务器，所以请求会走到 onFailure()，如需测试请换成自己的服务器。）
     */
    private void asyncPostMultipartRequestByOkHttp() {
        final String IMGUR_CLIENT_ID = "...";
        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
        //test.png 为 SD 卡跟目录下的文件，需要提前放好
        File file = new File(Environment.getExternalStorageDirectory(), "test.png");
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "test")
                .addFormDataPart("image", "test.png",
                        RequestBody.create(MEDIA_TYPE_PNG, file))
                .build();
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure IOException-->" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse data-->" + response.body().string());
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }

    /**
     * 异步 GET 请求并设置超时时间
     */
    private void asyncGetRequestByOkHttpAndSetTimeout() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(1, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/2") //该 url 的延迟时间为 2 秒
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure IOException-->" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse data-->" + response.body().string());
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
    }

    /**
     * 异步 GET 请求的同时并马上取消请求
     */
    private void asyncGetRequestByOkHttpAndCancelRequest() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/2") //该 url 的延迟时间为 2 秒
                .build();
        mCall = client.newCall(request);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure IOException-->" + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i(TAG, "onResponse data-->" + response.body().string());
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            }
        });
        //请求的同时马上取消请求
        mCall.cancel();
        Log.i(TAG, "asyncGetRequestByOkHttpAndCancelRequest isCanceled-->" + mCall.isCanceled());
    }

}
