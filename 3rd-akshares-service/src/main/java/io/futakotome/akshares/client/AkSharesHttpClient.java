package io.futakotome.akshares.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AkSharesHttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AkSharesHttpClient.class);
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public AkSharesHttpClient(OkHttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    private Map<String, String> obj2Map(Object object) {
        try {
            String jsonStr = objectMapper.writeValueAsString(object);
            return objectMapper.readValue(jsonStr, Map.class);
        } catch (JsonProcessingException e) {
            LOGGER.error("obj2Map失败", e);
            throw new RuntimeException(e);
        }
    }

    public String get(String url, Map<String, String> queryParameters) throws IOException {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url))
                .newBuilder();
        if (Objects.nonNull(queryParameters) && !queryParameters.isEmpty()) {
            queryParameters.forEach(urlBuilder::addQueryParameter);
        }
        Request.Builder requestBuilder = new Request.Builder()
                .url(urlBuilder.build())
                .get()
                .addHeader("Accept", "application/json");
        Request request = requestBuilder.build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return response.body().string();
            }
            throw new IOException("GET 请求失败:" + response.code());
        }
    }
}
