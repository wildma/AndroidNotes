package com.wildma.androidnotes.retrofit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${文件上传服务}
 */
public interface FileUploadService {

    // @Multipart、@Part
    @POST("?service=App.CDN.UploadImg")
    @Multipart
    Call<UploadImgBean> testFileUpload1(@Part MultipartBody.Part file, @Part("app_key") RequestBody appKey);

    // @Multipart、@PartMap
    @POST("?service=App.CDN.UploadImg")
    @Multipart
    Call<UploadImgBean> testFileUpload2(@PartMap Map<String, RequestBody> map);
}
