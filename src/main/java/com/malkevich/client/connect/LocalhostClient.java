package com.malkevich.client.connect;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

/**
 * Created by Stepan on 24.04.16.
 */
public interface LocalhostClient {
    @GET("/api/recommend/{user_id}")
    Call<List<String>> recommend(@Path("user_id") final String user_id);

    @GET("/api/greet/{user_id}")
    Call<String> greeting(@Path("user_id") final String user_id);
}
