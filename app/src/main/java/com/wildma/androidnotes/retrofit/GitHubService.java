package com.wildma.androidnotes.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Author       wildma
 * Github       https://github.com/wildma
 * Desc	        ${GitHub 服务}
 */
public interface GitHubService {

    @GET("users/{user}/repos")
    Call<ResponseBody> testPath(@Path("user") String user);
}
