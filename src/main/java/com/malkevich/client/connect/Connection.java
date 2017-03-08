package com.malkevich.client.connect;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by Stepan on 24.04.16.
 */
public class Connection {
    private static final String API_BASE_URL = "http://localhost:8080";
    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(API_BASE_URL);

    private static Retrofit retrofit = builder.build();
    private static LocalhostClient localhostClientInterface = retrofit.create(LocalhostClient.class);

    public List<String> getResult(final String user_id) throws IOException {
        Call<List<String>> call = localhostClientInterface.recommend(user_id);
        Response<List<String>> response = call.execute();
        return response.body();
    }
}
