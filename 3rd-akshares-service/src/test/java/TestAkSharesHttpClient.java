import com.fasterxml.jackson.databind.ObjectMapper;
import io.futakotome.akshares.AkSharesApplication;
import io.futakotome.akshares.client.AkSharesHttpClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;

@SpringBootTest(classes = AkSharesApplication.class)
@RunWith(SpringRunner.class)
public class TestAkSharesHttpClient {
    @Autowired
    private AkSharesHttpClient httpClient;

    @Test
    public void testGet() throws IOException {
        String body = httpClient.get("", new HashMap<>() {{
        }});

        ObjectMapper mapper = new ObjectMapper();
        //        ProductDto result = mapper.readValue(body, ProductDto.class);
        //        System.out.println(result.toString());
    }
}
