package com.wildma.androidnotes.retrofit;

import android.os.Environment;
import android.view.View;

import com.wildma.androidnotes.R;
import com.wildma.androidnotes.base.BaseActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${Retrofit 的使用}
 */
public class RetrofitActivity extends BaseActivity {

    @Override
    protected int initLayoutId() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void initView() {
        setTitle("Retrofit 的使用");
    }

    @Override
    protected void initPresenter() {
    }

    /**
     * 简单 GET 请求
     */
    public void testGetRequest(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<PostmanGetBean> call = service.testGet();
        call.enqueue(new Callback<PostmanGetBean>() {
            @Override
            public void onResponse(Call<PostmanGetBean> call, Response<PostmanGetBean> response) {
                System.out.println(response.body().getUrl());
            }

            @Override
            public void onFailure(Call<PostmanGetBean> call, Throwable t) {

            }
        });
    }

    /**
     * @HTTP 注解使用
     */
    public void testHTTP(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<PostmanGetBean> call = service.testHTTP();
        call.enqueue(new Callback<PostmanGetBean>() {
            @Override
            public void onResponse(Call<PostmanGetBean> call, Response<PostmanGetBean> response) {
                System.out.println(response.body().getUrl());
            }

            @Override
            public void onFailure(Call<PostmanGetBean> call, Throwable t) {

            }
        });
    }

    /**
     * @FormUrlEncoded、@Field 注解使用（单个键值对传）
     */
    public void testFormUrlEncoded1(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<PostmanPostBean> call = service.testFormUrlEncoded1("wildma", "123456");
        call.enqueue(new Callback<PostmanPostBean>() {
            @Override
            public void onResponse(Call<PostmanPostBean> call, Response<PostmanPostBean> response) {
                System.out.println(response.body().getForm().toString());
            }

            @Override
            public void onFailure(Call<PostmanPostBean> call, Throwable t) {

            }
        });
    }

    /**
     * @FormUrlEncoded、@FieldMap 注解使用（传入一个 Map 集合）
     */
    public void testFormUrlEncoded2(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Map<String, Object> map = new HashMap<>();
        map.put("username", "wildma");
        map.put("password", "123456");
        Call<PostmanPostBean> call = service.testFormUrlEncoded2(map);
        call.enqueue(new Callback<PostmanPostBean>() {
            @Override
            public void onResponse(Call<PostmanPostBean> call, Response<PostmanPostBean> response) {
                System.out.println(response.body().getForm().toString());
            }

            @Override
            public void onFailure(Call<PostmanPostBean> call, Throwable t) {

            }
        });
    }

    /**
     * @Multipart、@Part 注解使用（单文件上传）
     */
    public void testFileUpload1(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hn216.api.yesapi.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestBody appKey = RequestBody.create(null, "替换成你在 YESAPI 上获取的 appKey");
        // test.png 为 SD 卡跟目录下的文件，需要提前放好
        File file = new File(Environment.getExternalStorageDirectory(), "test.png");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        // 构建 MultipartBody.Part，其中 file 为服务器约定好的 key，test.png 为文件名称
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.png", requestBody);

        FileUploadService service = retrofit.create(FileUploadService.class);
        Call<UploadImgBean> call = service.testFileUpload1(filePart, appKey);
        call.enqueue(new Callback<UploadImgBean>() {
            @Override
            public void onResponse(Call<UploadImgBean> call, Response<UploadImgBean> response) {
                System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Call<UploadImgBean> call, Throwable t) {
            }
        });
    }

    /**
     * @Multipart、@PartMap 注解使用（多文件上传）
     */
    public void testFileUpload2(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://hn216.api.yesapi.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestBody appKey = RequestBody.create(null, "替换成你在 YESAPI 上获取的 appKey");
        //test.png 为 SD 卡跟目录下的文件，需要提前放好
        File file = new File(Environment.getExternalStorageDirectory(), "test.png");
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestBodyMap.put("app_key", appKey);
        // 加入一个文件，其中 file 为服务器约定好的 key，test.png 为文件名称
        requestBodyMap.put("file\"; filename=\"test.png", requestBody);
        // 有更多文件，则继续 put()...

        FileUploadService service = retrofit.create(FileUploadService.class);
        Call<UploadImgBean> call = service.testFileUpload2(requestBodyMap);
        call.enqueue(new Callback<UploadImgBean>() {
            @Override
            public void onResponse(Call<UploadImgBean> call, Response<UploadImgBean> response) {
                System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Call<UploadImgBean> call, Throwable t) {
            }
        });
    }

    /**
     * @Streaming 注解使用（文件下载）
     */
    public void testFileDownload(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wildma.github.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FileDownloadService service = retrofit.create(FileDownloadService.class);
        Call<ResponseBody> call = service.testFileDownload();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream is = response.body().byteStream();
                System.out.println("开始保存文件");
                // 保存文件...
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @Header 注解使用
     */
    public void testHeader(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testHeaders();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    /**
     * @Headers 注解使用
     */
    public void testHeaders(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testHeader("123");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @Headers 注解使用（多个请求头）
     */
    public void testHeaders2(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testHeaders2();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @HeaderMap 注解使用
     */
    public void testHeaderMap(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> map = new HashMap<>();
        map.put("token", "123");
        map.put("sign", "456");

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testHeaderMap(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @Body 注解使用
     */
    public void testBody(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TestBodyBean bean = new TestBodyBean();
        bean.setUsername("wildma");
        bean.setPassword("123456");

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testBody(bean);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    /**
     * @Query 注解使用
     */
    public void testQuery(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testQuery("wildma");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @QueryMap 注解使用
     */
    public void testQueryMap(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Map<String, String> map = new HashMap<>();
        map.put("username", "wildma");
        map.put("age", "18");

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testQueryMap(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @QueryName 注解使用
     */
    public void testQueryName(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testQueryName("wildma", "tom");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @Path 注解使用
     */
    public void testPath(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubService service = retrofit.create(GitHubService.class);
        Call<ResponseBody> call = service.testPath("wildma");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * @Url 注解使用
     */
    public void testUrl(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testUrl("https://postman-echo.com/get");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * 设置自定义的 OkHttpClient
     */
    public void testCustomOkHttpClient(View view) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request = originalRequest.newBuilder()
                        .header("token", "123")
                        .header("sign", "456")
                        .build();
                return chain.proceed(request);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)// 设置自定义的 OkHttpClient
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Call<ResponseBody> call = service.testCustomOkHttpClient();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    /**
     * CallAdapter 的使用
     */
    public void testCallAdapter(View view) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postman-echo.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// 设置 RxJava 作为当前的 CallAdapter
                .build();

        PostmanService service = retrofit.create(PostmanService.class);
        Observable<ResponseBody> observable = service.testCallAdapter();
        observable.subscribeOn(Schedulers.io())               // 在 IO 线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 在主线程处理请求结果
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            System.out.println(responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
