import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestReactiveRedis {
    @Autowired
    private ReactiveStringRedisTemplate redisTemplate;
    private ReactiveListOperations<String, String> reactiveListOperations;

    @Before
    public void setup() {
        reactiveListOperations = redisTemplate.opsForList();
    }

    @Test
    public void givenListAndValues_whenLeftPushAndLeftPop_thenLeftPushAndLeftPop() {
        Mono<Long> lPush = reactiveListOperations.leftPushAll("Test list", "first", "second")
                .log("Pushed");
    }
}
