import io.trizgay.quantx.MainVerticle;
import io.trizgay.quantx.http.HttpVerticle;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class MainVerticleTest {
    Vertx vertx;

    @BeforeEach
    void prepare() {
        vertx = Vertx.vertx();
    }

    @Test
    void deploy(VertxTestContext context) {
        vertx.deployVerticle(MainVerticle.class.getName(),
                context.succeeding(id -> {
                    System.out.println(id);
                    context.completeNow();
                }));
    }
}
