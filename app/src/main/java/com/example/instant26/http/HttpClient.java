package com.example.instant26.http;

import android.os.Build;
import android.support.annotation.RequiresApi;
import okhttp3.*;

import java.util.concurrent.TimeUnit;

public class HttpClient {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String post(String url, String json, Callback callback) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(callback);


        } catch (Exception e) {
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String get(String url, Callback callback) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .build();

            Call call = client.newCall(request);
            call.enqueue(callback);


        } catch (Exception e) {
        }
        return "";
    }
}
