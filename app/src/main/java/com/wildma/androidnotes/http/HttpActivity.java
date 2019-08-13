package com.wildma.androidnotes.http;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.wildma.androidnotes.R;
import com.wildma.androidnotes.base.BaseActivity;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Author   wildma
 * Date     2019/08/11
 * Desc     ${HttpClient 与 HttpURLConnection 的使用}
 */
public class HttpActivity extends BaseActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_http;
    }

    @Override
    protected void initView() {
        setTitle("HttpClient 与 HttpURLConnection 的使用");
    }

    @Override
    protected void initPresenter() {

    }

    public void getRequestByHttpClient(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRequestByHttpClient();
            }
        }).start();
    }

    public void postRequestByHttpClient(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postRequestByHttpClient();
            }
        }).start();
    }

    public void getRequestByHttpURLConnection(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRequestByHttpURLConnection();
            }
        }).start();
    }

    public void postRequestByHttpURLConnection(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postRequestByHttpURLConnection();
            }
        }).start();
    }

    /**
     * HttpClient 的 GET 请求
     */
    private void getRequestByHttpClient() {
        //1. 创建 HttpClient 对象（DefaultHttpClient 为 HttpClient 的实现类）
        HttpClient httpClient = new DefaultHttpClient();
        //2. 创建请求对象（这里是 GET 请求），参数为请求地址
        HttpGet httpGet = new HttpGet("https://www.baidu.com");
        try {
            //3.调用 HttpClient 对象的 execute 方法发送请求，返回 HttpResponse
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //4. 检查是否请求成功，状态码 200 表示成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //5. 调用 HttpEntity 对象的 getContent 方法获取响应数据的输入流
                InputStream inputStream = httpResponse.getEntity().getContent();
                //6. 将输入流转换成字符串
                String data = inputStream2String(inputStream);
                Log.i(TAG, "data: " + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * HttpClient 的 POST 请求
     */
    private void postRequestByHttpClient() {
        //1. 创建 HttpClient 对象（DefaultHttpClient 为 HttpClient 的实现类）
        HttpClient httpClient = new DefaultHttpClient();
        //2. 创建请求对象（这里是 POST 请求），参数为请求地址（这里用的是 postman 提供的测试地址）
        HttpPost httpPost = new HttpPost("https://postman-echo.com/post");
        try {
            //3. 调用 HttpPost 对象的 setEntity 方法设置需要的参数
            List<NameValuePair> postParams = new ArrayList<>();
            postParams.add(new BasicNameValuePair("username", "wildma"));
            postParams.add(new BasicNameValuePair("password", "123456"));
            httpPost.setEntity(new UrlEncodedFormEntity(postParams));

            //4.调用 HttpClient 对象的 execute 方法发送请求，返回 HttpResponse
            HttpResponse httpResponse = httpClient.execute(httpPost);
            //5. 检查是否请求成功，状态码 200 表示成功
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //6. 调用 HttpEntity 对象的 getContent 方法获取响应数据的输入流
                InputStream inputStream = httpResponse.getEntity().getContent();
                //7. 将输入流转换成字符串
                String data = inputStream2String(inputStream);
                Log.i(TAG, "data: " + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * HttpURLConnection 的 GET 请求
     */
    private void getRequestByHttpURLConnection() {
        try {
            //1. 创建 URL 对象
            URL url = new URL("https://www.baidu.com");
            //2. 调用 URL 对象的 openConnection 方法获取 HttpURLConnection 实例
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //3. 设置请求方法（这里是 GET 请求）
            httpURLConnection.setRequestMethod("GET");
            //4. 连接
            httpURLConnection.connect();
            //5. 检查是否请求成功，状态码 200 表示成功
            if (httpURLConnection.getResponseCode() == 200) {
                //6. 调用 HttpURLConnection 对象的 getInputStream 方法获取响应数据的输入流
                InputStream inputStream = httpURLConnection.getInputStream();
                //7. 将输入流转换成字符串
                String data = inputStream2String(inputStream);
                Log.i(TAG, "data: " + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * HttpURLConnection 的 POST 请求
     */
    private void postRequestByHttpURLConnection() {
        try {
            //1. 创建 URL 对象
            URL url = new URL("https://postman-echo.com/post");
            //2. 调用 URL 对象的 openConnection 方法获取 HttpURLConnection 实例
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //3. 设置请求方法（这里是 POST 请求）
            httpURLConnection.setRequestMethod("POST");
            /*4. 设置允许输入输出*/
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            /*5. 设置请求参数*/
            String params = new StringBuilder()
                    .append("username=" + URLEncoder.encode("wildma", "UTF-8"))
                    .append("&password=" + URLEncoder.encode("123456", "UTF-8")).toString();
            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(params.getBytes());
            outputStream.flush();
            outputStream.close();

            //6. 连接
            httpURLConnection.connect();
            //7. 检查是否请求成功，状态码 200 表示成功
            if (httpURLConnection.getResponseCode() == 200) {
                //8. 调用 HttpURLConnection 对象的 getInputStream 方法获取响应数据的输入流
                InputStream inputStream = httpURLConnection.getInputStream();
                //9. 将输入流转换成字符串
                String data = inputStream2String(inputStream);
                Log.i(TAG, "data: " + data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将输入流转换成字符串
     *
     * @param inputStream 输入流
     * @return 字符串数据
     */
    private String inputStream2String(InputStream inputStream) {
        String data = "";
        BufferedReader bufferedReader = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            data = stringBuilder.toString();
            if (!TextUtils.isEmpty(data)) {
                //去除最后一个多余的换行符
                data = data.substring(0, data.length() - 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
