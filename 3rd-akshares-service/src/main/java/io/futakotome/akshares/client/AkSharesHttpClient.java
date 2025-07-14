package io.futakotome.akshares.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.akshares.config.AkToolsConfig;
import io.futakotome.akshares.dto.SHStockSummary;
import io.futakotome.akshares.dto.SZSummary;
import io.futakotome.akshares.dto.StockItem;
import io.futakotome.akshares.dto.StockZhIndex;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class AkSharesHttpClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(AkSharesHttpClient.class);
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final AkToolsConfig akToolsConfig;

    public AkSharesHttpClient(OkHttpClient httpClient, ObjectMapper objectMapper, AkToolsConfig akToolsConfig) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.akToolsConfig = akToolsConfig;
    }

    public List<StockZhIndex> fetchStockZhIndies(String symbol) {
        try {
            String body = getFromAkTools("api/public/stock_zh_index_spot_em", new HashMap<>() {{
                put("Accept", "application/json");
            }}, new HashMap<>() {{
                put("symbol", symbol);
            }});
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询A股指数失败", e);
        }
    }

    //todo 雪球-查询公司简介


    //东财-查询股票信息
    public List<StockItem> fetchBigAStockIndividual(String symbol) {
        try {
            String body = getFromAkTools("api/public/stock_individual_info_em", new HashMap<>() {{
                put("Accept", "application/json");
            }}, new HashMap<>() {{
                put("symbol", symbol);
            }});
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询个股信息失败", e);
        }
    }

    public List<SZSummary> fetchSZSummaries() {
        try {
            String body = getFromAkTools("api/public/stock_szse_summary", new HashMap<>() {{
                put("Accept", "application/json");
            }}, new HashMap<>() {{
                put("date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            }});
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询深圳证券交易所股票市场总貌失败", e);
        }
    }

    public List<SHStockSummary> fetchSHStockSummaries() {
        try {
            String body = getFromAkTools("api/public/stock_sse_summary", new HashMap<>() {{
                put("Accept", "application/json");
            }}, null);
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询上海证券交易所股票市场总貌失败", e);
        }
    }
    //    public

    public String getFromAkTools(String url, Map<String, String> headers, Map<String, String> queryParameters) throws IOException {
        HttpUrl.Builder urlBuilder = new HttpUrl.Builder()
                .scheme("http")
                .addPathSegment(url)
                .host(akToolsConfig.getHost())
                .port(akToolsConfig.getPort());
        if (Objects.nonNull(queryParameters) && !queryParameters.isEmpty()) {
            queryParameters.forEach(urlBuilder::addQueryParameter);
        }
        Request.Builder requestBuilder = new Request.Builder();
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            headers.forEach(requestBuilder::addHeader);
        }
        requestBuilder.url(urlBuilder.build()).get();
        Request request = requestBuilder.build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && Objects.nonNull(response.body())) {
                return response.body().string();
            }
            throw new IOException("GET 请求失败:" + response.code());
        }
    }
}
