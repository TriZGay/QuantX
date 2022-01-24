import io.trizgay.quantx.MainVerticle;
import io.trizgay.quantx.http.HttpVerticle;
import io.trizgay.quantx.http.pojo.GetPlateSetRequest;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
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

//    @Test
//    void deploy(VertxTestContext context) {
//        vertx.deployVerticle(MainVerticle.class.getName(),
//                context.succeeding(id -> {
//                    System.out.println(id);
//                    context.completeNow();
//                }));
//    }

    @Test
    void GetPlateSetReqTest(VertxTestContext context) {
        WebClient client = WebClient.create(vertx);
        vertx.deployVerticle(MainVerticle.class.getName(),
                context.succeeding(id -> {
                    client.post(8900, "0.0.0.0", "/quantx/api/v1/plateInfo")
                            .as(BodyCodec.string())
                            .sendJson(new GetPlateSetRequest(1, 1).toJson(), context.succeeding(resp -> {
                                context.verify(() -> {
                                    System.out.println(resp.body());
                                    context.completeNow();
                                });
                            }));
                }));
    }
}
