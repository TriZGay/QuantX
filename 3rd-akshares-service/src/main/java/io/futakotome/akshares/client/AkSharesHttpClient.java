package io.futakotome.akshares.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.akshares.config.AkToolsConfig;
import io.futakotome.akshares.controller.vo.StockZhHistoryRequest;
import io.futakotome.akshares.dto.*;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final AkToolsConfig akToolsConfig;

    public AkSharesHttpClient(OkHttpClient httpClient, ObjectMapper objectMapper, AkToolsConfig akToolsConfig) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.akToolsConfig = akToolsConfig;
    }

    //查询大A历史行情数据
    public List<StockZhHistory> fetchStockZhHistory(StockZhHistoryRequest request) {
        Map<String, String> requestMap = new HashMap<>();
        if (Objects.nonNull(request.getSymbol())) {
            requestMap.put("symbol", request.getSymbol());
        }
        if (Objects.nonNull(request.getPeriod())) {
            requestMap.put("period", request.getPeriod());
        }
        if (Objects.nonNull(request.getStartDate())) {
            requestMap.put("start_date", request.getStartDate());
        }
        if (Objects.nonNull(request.getEndDate())) {
            requestMap.put("end_date", request.getEndDate());
        }
        if (Objects.nonNull(request.getAdjust())) {
            requestMap.put("adjust", request.getAdjust());
        }
        try {
            String body = getFromAkTools("api/public/stock_zh_a_hist", new HashMap<>() {{
                put("Accept", "application/json");
            }}, requestMap);
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询历史行情失败", e);
        }
    }

    //查询行情报价
    public List<StockBidAskItem> fetchStockBidAsk(String symbol) {
        try {
            String body = getFromAkTools("api/public/stock_bid_ask_em", new HashMap<>() {{
                put("Accept", "application/json");
            }}, new HashMap<>() {{
                put("symbol", symbol);
            }});
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询行情报价失败", e);
        }
    }

    //查询沪深京A-实时行情-东财
    public List<StockRTPrice> fetchAllZhStockRtPrice() {
        try {
            String body = getFromAkTools("api/public/stock_zh_a_spot_em", new HashMap<>() {{
                put("Accept", "application/json");
            }}, null);
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询沪深京A-实时行情失败", e);
        }
    }

    //查询沪A-实时行情-东财
    public List<StockRTPrice> fetchShStockRtPrice() {
        try {
            String body = getFromAkTools("api/public/stock_sh_a_spot_em", new HashMap<>() {{
                put("Accept", "application/json");
            }}, null);
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询沪A-实时行情失败", e);
        }
    }

    //查询深A-实时行情-东财
    public List<StockRTPrice> fetchSzStockRtPrice() {
        try {
            String body = getFromAkTools("api/public/stock_sz_a_spot_em", new HashMap<>() {{
                put("Accept", "application/json");
            }}, null);
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询深A-实时行情失败", e);
        }
    }

    //查询京A-实时行情-东财
    public List<StockRTPrice> fetchBjStockRtPrice() {
        try {
            String body = getFromAkTools("api/public/stock_bj_a_spot_em", new HashMap<>() {{
                put("Accept", "application/json");
            }}, null);
            return objectMapper.readValue(body, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("查询京A-实时行情失败", e);
        }
    }
    //查询新股-实时行情-东财

    //查询A股-股票指数
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

    //雪球-查询公司简介
    public List<StockItem> fetchBigAStockIndividualInfo(String symbol) {
        try {
            String body = getFromAkTools("api/public/stock_individual_basic_info_xq", new HashMap<>() {{
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

    //查询深交所市场概况
    public List<StockSzSummary> fetchSZSummaries() {
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

    //查询上交所市场概况
    public List<StockShSummary> fetchSHStockSummaries() {
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
