import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.akshares.AkSharesApplication;
import io.futakotome.akshares.client.AkSharesHttpClient;
import io.futakotome.akshares.dto.StockShSummary;
import io.futakotome.akshares.dto.StockZhHistory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@SpringBootTest(classes = AkSharesApplication.class)
@RunWith(SpringRunner.class)
public class TestAkSharesHttpClient {
    @Autowired
    private AkSharesHttpClient httpClient;

    @Test
    public void testSummaries() throws IOException {
        String body = httpClient.getFromAkTools("api/public/stock_sse_summary", new HashMap<>() {{
            put("Accept", "application/json");
        }}, new HashMap<>() {{
        }});
        //                                System.out.println(body);
        ObjectMapper mapper = new ObjectMapper();
        List<StockShSummary> result = mapper.readValue(body, new TypeReference<>() {
        });
        String r = mapper.writeValueAsString(result);
        System.out.println(r);
    }

    @Test
    public void testGet() throws IOException {
        String body = httpClient.getFromAkTools("api/public/stock_zh_a_hist", new HashMap<>() {{
            put("Accept", "application/json");
        }}, new HashMap<>() {{
            put("symbol", "000001");
            put("period", "daily");
            put("start_date", "20250714");
            put("end_date", "20250715");
        }});
        //                        System.out.println(body);
        ObjectMapper mapper = new ObjectMapper();
        List<StockZhHistory> result = mapper.readValue(body, new TypeReference<>() {
        });
        System.out.println(result.toString());
    }
}
