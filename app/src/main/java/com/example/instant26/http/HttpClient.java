package com.example.instant26.http;

import android.os.Build;
import android.support.annotation.RequiresApi;
import okhttp3.*;

public class HttpClient {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String post(String url, String json, Callback callback) {
        try {
            OkHttpClient client = new OkHttpClient();
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
}
