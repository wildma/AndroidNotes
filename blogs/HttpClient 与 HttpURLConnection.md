# 1 前言
开发一个 Android 应用必不可少的就是网络通信，而网络通信一般都会使用 HTTP 协议来发送和接收网络数据，在 Android 中可以通过 HttpClient 或 HttpURLConnection 来操作 HTTP，这两种方式都支持 HTTPS 协议，以流的形式进行上传和下载，配置超时时间，IPV6，连接池等功能。

# 2 HttpClient 与 HttpURLConnection 介绍
### HttpClient
HttpClient 是 Apache 提供的开源库，可以用来提供高效的、最新的、功能丰富的支持 HTTP 协议的客户端编程工具包。原本 Android SDK 中是包含了 HttpClient 的，但是 Google 在 Android 6.0 版本已经删除了该库的相关代码，只能通过依赖库的方式进行使用。

### HttpURLConnection
HttpURLConnection 是 java.net 包中提供用来访问 HTTP 协议的基本功能的类，它继承自 URLConnection，可用于向指定网站发送 GET 请求、POST 请求。HttpURLConnection 相对于 HttpClient 没什么封装，但也是因为这个我们可以更容易的去扩展它。

不过在 Android 2.2 版本之前，HttpURLConnection 一直存在着一些令人厌烦的 bug。比如说对一个可读的 InputStream 调用 close() 方法时，就有可能会导致连接池失效了。那么我们通常的解决办法就是直接禁用掉连接池的功能，如下：
```
private void disableConnectionReuseIfNecessary() {
      // 这是一个2.2版本之前的bug
      if (Integer.parseInt(Build.VERSION.SDK) < Build.VERSION_CODES.FROYO) {
            System.setProperty("http.keepAlive", "false");
      }
}
```
# 3 HttpClient 的使用
### 3.1 使用前准备
1. 加入网络权限
```
<uses-permission android:name="android.permission.INTERNET"/>
```
2. 如果使用 Android Studio，则需要在使用的 module 下的 build.gradle 中加入如下：
```
android {
    useLibrary 'org.apache.http.legacy'
}
```
3. 从 Android 9 开始，默认情况下该库已从 bootclasspath 中移除且不可用于应用。
要继续使用 Apache HTTP 客户端，以 Android 9 及更高版本为目标的应用可以向其 AndroidManifest.xml 的  application 节点下添加以下内容（具体可看-[Apache HTTP 客户端弃用](https://developer.android.google.cn/about/versions/pie/android-9.0-changes-28)）：
```
<uses-library android:name="org.apache.http.legacy" android:required="false"/>
```


### 3.2 HttpClient 的 GET 请求
使用流程已在注释中详细描述，具体代码如下：
```
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
```
其中，inputStream2String 方法如下：
```
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
```
接下来直接调用就可以了，需要注意的是网络请求需要在子线程中调用，如下：
```
        new Thread(new Runnable() {
            @Override
            public void run() {
                getRequestByHttpClient();
            }
        }).start();
```
打印结果如下：
```
<!DOCTYPE html>
    <!--STATUS OK--><html> <head><meta http-equiv=content-type content=text/html;charset=utf-8><meta http-equiv=X-UA-Compatible content=IE=Edge><meta content=always name=referrer><link rel=stylesheet type=text/css href=http://s1.bdstatic.com/r/www/cache/bdorz/baidu.min.css>
<title>百度一下，你就知道</title>
</head> <body link=#0000cc> 省略部分日志...</body> 
</html>
```
### 3.3 HttpClient 的 POST 请求
POST 请求与 GET 请求类似，不同的是需要将请求对象 HttpGet 换成 HttpPost，然后通过 HttpPost 的 setEntity 方法设置请求参数。使用流程已在注释中详细描述，具体代码如下：
```
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
```
在子线程中调用该方法，打印结果如下：
```
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

# 4 HttpURLConnection 的使用
### 4.1 使用前准备
加入网络权限
```
<uses-permission android:name="android.permission.INTERNET"/>
```
### 4.2 HttpURLConnection 的 GET 请求
使用流程已在注释中详细描述，具体代码如下：
```
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
```
在子线程中调用该方法，打印结果与 HttpClient 的 GET 请求一样。
### 4.3 HttpURLConnection 的 POST 请求
POST 请求与 GET 请求类似，不同的是需要将请求方法 GET 换成 POST，然后通过输出流设置请求参数，还需要设置允许输入输出，即 setDoInput(true) 与 setDoOutput(true)。使用流程已在注释中详细描述，具体代码如下：
```
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
```
在子线程中调用该方法，打印结果与 HttpClient 的 POST 请求一样。

# 5 HttpClient 与 HttpURLConnection 如何选择？
在 Android 2.2 版本之前，HttpClient 拥有较少的 bug，因此使用它是最好的选择。

而在 Android 2.3 版本及以后，HttpURLConnection 则是最佳的选择。它的 API 简单，体积较小，因而非常适用于Android 项目。压缩和缓存机制可以有效地减少网络访问的流量，在提升速度和省电方面也起到了较大的作用。

其实参考 Google 的网络框架 Volley 也能得出结论，Volley 源码中在 Android 2.3 及以上版本，使用的是 HttpURLConnection，而在 Android 2.2 及以下版本，使用的是 HttpClient。



# 6 源码
[http](https://github.com/wildma/AndroidNotes/tree/master/app/src/main/java/com/wildma/androidnotes/http)
