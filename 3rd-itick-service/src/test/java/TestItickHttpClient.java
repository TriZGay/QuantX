import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.itick.ItickFetcherApplication;
import io.futakotome.itick.client.ItickHttpClient;
import io.futakotome.itick.config.ItickConfig;
import io.futakotome.itick.dto.StockDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;

@SpringBootTest(classes = ItickFetcherApplication.class)
@RunWith(SpringRunner.class)
public class TestItickHttpClient {
    @Autowired
    private ItickHttpClient tickHttpClient;
    @Autowired
    private ItickConfig config;

    @Test
    public void testGet() throws IOException {
        String body = tickHttpClient.get("https://api.itick.org/symbol/list?type=stock&region=hk&code=700", new HashMap<>() {{
            put("accept", "application/json");
            put("token", config.getApikey());
        }});
        ObjectMapper mapper = new ObjectMapper();
        StockDto result = mapper.readValue(body, StockDto.class);
        System.out.println(result.toString());
    }
}
