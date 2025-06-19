package io.futakotome.itick.client;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
public class ItickHttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ItickHttpClient.class);
    private final OkHttpClient httpClient;

    public ItickHttpClient(OkHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String get(String url, Map<String, String> headers) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .get();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = requestBuilder.build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return response.body().string();
            }
            throw new IOException("GET 请求失败:" + response.code());
        }
    }
}
