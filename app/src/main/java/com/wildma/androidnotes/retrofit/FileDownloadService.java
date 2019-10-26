package com.wildma.androidnotes.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${文件下载服务}
 */
public interface FileDownloadService {

    // @Streaming
    @Streaming
    @GET("medias/avatars/avatar.jpg")
    Call<ResponseBody> testFileDownload();

}
