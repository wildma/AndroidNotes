package com.wildma.androidnotes.retrofit;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import retrofit2.http.Url;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${postman 服务}
 */
public interface PostmanService {

    // @GET
    @GET("get")
    Call<PostmanGetBean> testGet();

    // @HTTP
    @HTTP(method = "GET", path = "get", hasBody = false)
    Call<PostmanGetBean> testHTTP();

    // @FormUrlEncoded、@Field
    @POST("post")
    @FormUrlEncoded
    Call<PostmanPostBean> testFormUrlEncoded1(@Field("username") String name, @Field("password") String password);

    // @FormUrlEncoded、@FieldMap
    @POST("post")
    @FormUrlEncoded
    Call<PostmanPostBean> testFormUrlEncoded2(@FieldMap Map<String, Object> map);

    // @Header
    @GET("headers")
    Call<ResponseBody> testHeader(@Header("token") String token);

    // @Headers
    @Headers("token: 123")
    @GET("headers")
    Call<ResponseBody> testHeaders();

    // @Headers 多个请求头
    @Headers({"token: 123", "sign: 456"})
    @GET("headers")
    Call<ResponseBody> testHeaders2();

    // @HeaderMap
    @GET("headers")
    Call<ResponseBody> testHeaderMap(@HeaderMap Map<String, String> map);

    // @Body
    @POST("post")
    Call<ResponseBody> testBody(@Body TestBodyBean testBodyBean);

    // @Query
    @GET("get")
    Call<ResponseBody> testQuery(@Query("username") String username);

    // @QueryMap
    @GET("get")
    Call<ResponseBody> testQueryMap(@QueryMap Map<String, String> params);

    // @QueryName
    @GET("get")
    Call<ResponseBody> testQueryName(@QueryName String... filters);

    // @Url
    @GET()
    Call<ResponseBody> testUrl(@Url String url);

    // OkHttpClient
    @GET("get")
    Call<ResponseBody> testCustomOkHttpClient();

    // CallAdapter
    @GET("get")
    Observable<ResponseBody> testCallAdapter();

}
