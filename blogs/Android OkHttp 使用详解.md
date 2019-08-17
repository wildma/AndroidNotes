# Android OkHttp 使用详解

# 1 OkHttp 介绍
上一篇介绍了 HttpClient 与 HttpURLConnection，我们知道 Google 在 Android 6.0 版本已经删除了 HttpClient 的相关代码，HttpURLConnection 用起来也比较麻烦，所以网络框架 OkHttp 也就诞生了。

OkHttp 是 Square 公司开源的网络框架，可以说是当前 Android 界最好用的网络框架了，它有如下特点：
1. 封装简单易用，支持链式调用。
2. 同时支持同步和异步请求。
3. 支持 HTTP/2 协议，允许对同一主机的所有请求共用同一个 socket 连接。
4. 如果 HTTP/2 不可用, 连接池复用技术可以减少请求延迟。
5. 支持 GZIP，减小了下载大小。
6. 支持缓存处理，可以避免重复请求。
7. 如果你的服务有多个 IP 地址，当第一次连接失败，OkHttp 会尝试备用地址。
8. OkHttp 还处理了代理服务器问题和SSL握手失败问题。

# 2 OkHttp 的使用
### 2.1 使用前准备
1. 加入网络权限
在 AndroidManifest.xml 文件中加入如下：
```
<uses-permission android:name="android.permission.INTERNET"/>
```
2. 添加 OkHttp 库的依赖
在当前使用的 module 下的 build.gradle 中加入如下：
```
implementation 'com.squareup.okhttp3:okhttp:3.4.1'
```

### 2.2 同步 GET 请求
同步 GET 请求的步骤：
1. 创建 OkHttpClient 对象。
2. 创建 Request 对象，然后通过 Builder() 链式调用可以设置请求 url、header、method 等。
3. 调用 OkHttpClient 对象的 newCall() 方法创建一个 Call 对象。
4. 调用 Call 对象的 execute() 方法发起一个请求，并获取服务器返回的数据。
5. Response 就是返回的数据，可以根据需求得到相应的数据格式，例如：
希望返回 String，则调用 response.body().string()，适用于不超过 1 MB 的数据。
希望返回输入流，则调用 response.body().byteStream()，适用于超过 1 MB 的数据，例如下载文件。
希望返回二进制字节数组，则调用 response.body().bytes()

需要注意的是：
1. 同步 GET 请求需要在子线程中调用。
2. string()、bytes() 方法只能调用一次，原因是这两个方法在第一次调用完就关闭了流。

具体代码如下：
```
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
```
打印结果：
```
OkHttpActivity: syncGetRequestByOkHttp data-->
<!DOCTYPE html>
    <!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css>
<title>百度一下，你就知道</title>
</head> <body link=#0000cc> 省略部分日志...</body> 
</html>
```

### 2.3 异步 GET 请求
异步 GET 请求与同步 GET 请求的代码差不多，区别是：
1. 将同步方法 execute() 换成异步方法 enqueue()。
2. 异步方法不需要在子线程中执行，因为 enqueue() 方法内部已经有一个线程池去执行。
3. 返回的数据在 onResponse() 方法中，由于内部是通过线程池去执行的，所以该方法也在子线程。如果需要操作 UI，需要使用 handler 等切换到主线程。

具体代码如下：
```
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
```
打印结果：与同步 GET 请求一样。

### 2.4 异步 POST 请求
POST 请求与 GET 请求的区别是 POST 请求需要构建一个 RequestBody 来存放请求参数，然后在 Request.Builder 中调用 post 方法，并传入 RequestBody 对象。具体代码如下：
```
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
```
打印结果：
```
OkHttpActivity: onResponse data-->
{
 "args": {},
 "data": "",
 "files": {},
 "form": {
  "username": "wildma",
  "password": "123456"
 },
 "headers": {
  "x-forwarded-proto": "https",
  "host": "postman-echo.com",
  "content-length": "31",
  "content-type": "application/x-www-form-urlencoded",
  "user-agent": "Apache-HttpClient/UNAVAILABLE (java 1.4)",
  "x-forwarded-port": "443"
 },
 "json": {
  "username": "wildma",
  "password": "123456"
 },
 "url": "https://postman-echo.com/post"
}
```

### 2.5 异步 POST 方式上传文件
上传文件首先需要定义上传文件的类型 MediaType，然后构建一个 File 的 RequestBody，其他与普通 POST 请求类似。  
其中 test.txt 为 SD 卡跟目录下的文件，内容为“test post file”，需要提前放好。具体代码如下：
```
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
```
注意，由于需要操作 SD 卡数据，所以需要在 AndroidManifest.xml 文件添加读写权限，如下：
```
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
```
如果是 6.0 以上还需要动态申请权限。

打印结果：
```
OkHttpActivity: onResponse data-->test post file
```

### 2.6 异步 POST multipart 请求
MultipartBody.Builder 可以构建与 HTML 文件上传表单兼容的复杂请求体。multipart 请求体的每一部分本身就是一个请求体，可以定义自己的请求头。也就是说一个接口可能需要同时上传文件和其他参数，这时候就可以使用 MultipartBody.Builder 来构建复杂的请求体。具体代码如下：
```
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
```
打印结果：
由于这里没有合适的测试服务器，所以请求会走到 onFailure()，如需测试请换成自己的服务器。

### 2.7 设置超时时间
发送一个请求如果没有响应则会使用超时结束 call，没有响应可能是客户端或服务器问题，例如网络慢导致请求失败，OkHttp 可以设置连接、读取和写入的超时时间。
如下请求 url 的延迟时间为 2 秒，这时候我设置读取的超时时间为 1 秒，最终则会请求失败走到 onFailure() 方法。具体代码如下：
```
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
```
打印结果：
```
OkHttpActivity: onFailure IOException-->java.net.SocketTimeoutException: timeout
```
说明 socket 超时了。

### 2.8 取消请求
通过 Call.cancel() 可以立即停止正在进行的 Call。如果一个线程正在写请求或读响应，它还将收到一个 IOException 异常。当一个 Call 不需要时，可以使用 Call.cancel()  节约网络资源，例如用户离开一个界面时。同步和异步调用都可以被取消。
如下请求 url 的延迟时间为 2 秒，这时候我在请求的同时马上取消请求。具体代码如下：
```
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
```
打印结果：
```
OkHttpActivity: asyncGetRequestByOkHttpAndCancelRequest isCanceled-->true
OkHttpActivity: onFailure IOException-->java.io.IOException: Canceled
```
第一行说明取消成功了，第二行说明一个线程正在写请求或读响应，这时候会走到 onFailure() 方法并收到一个 IOException 异常。

# 3 源码
[OkHttp 的使用 Demo]()