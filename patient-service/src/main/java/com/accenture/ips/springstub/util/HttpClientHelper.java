package com.accenture.ips.springstub.util;

import com.accenture.ips.springstub.dto.HttpResponseDto;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class HttpClientHelper {

    @Autowired
    private OkHttpClient okHttpClient;

    public HttpResponseDto get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            HttpResponseDto res = new HttpResponseDto();
            res.setHttpStatus(response.code());
            res.setResponseBody(response.body().string());

            return res;
        }
    }
}
