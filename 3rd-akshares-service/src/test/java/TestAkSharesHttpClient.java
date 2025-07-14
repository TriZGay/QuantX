import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.akshares.AkSharesApplication;
import io.futakotome.akshares.client.AkSharesHttpClient;
import io.futakotome.akshares.dto.StockItem;
import io.futakotome.akshares.dto.StockZhIndex;
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
    public void testGet() throws IOException {
        String body = httpClient.getFromAkTools("api/public/stock_individual_basic_info_xq", new HashMap<>() {{
            put("Accept", "application/json");
        }}, new HashMap<>() {{
            put("symbol", "SH601127");
        }});
                System.out.println(body);
//        ObjectMapper mapper = new ObjectMapper();
//        List<StockZhIndex> result = mapper.readValue(body, new TypeReference<>() {
//        });
//        System.out.println(result.toString());
    }
}
