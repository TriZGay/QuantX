import io.trizgay.quantx.MainVerticle;
import io.trizgay.quantx.http.pojo.PostIpoInfoRequest;
import io.trizgay.quantx.http.pojo.PostPlateSetRequest;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

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

//    @Test
//    void postPlateSetReqTest(VertxTestContext context) {
//        WebClient client = WebClient.create(vertx);
//        vertx.deployVerticle(MainVerticle.class.getName(),
//                context.succeeding(id -> {
//                    client.post(8900, "0.0.0.0", "/quantx/api/v1/plateInfo")
//                            .as(BodyCodec.string())
//                            .sendJson(new PostPlateSetRequest(41, 0).toJson(), context.succeeding(resp -> {
//                                context.verify(() -> {
//                                    assertThat(resp.statusCode()).isEqualTo(200);
//                                    context.completeNow();
//                                });
//                            }));
//                }));
//        try {
//            Thread.sleep(1000 * 60);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    void postIpoInfo(VertxTestContext context) {
        WebClient client = WebClient.create(vertx);
        vertx.deployVerticle(MainVerticle.class.getName(),
                context.succeeding(id -> {
                    client.post(8900, "0.0.0.0", "/quantx/api/v1/ipoInfo")
                            .as(BodyCodec.string())
                            .sendJson(new PostIpoInfoRequest(1).toJson(), context.succeeding(resp -> {
                                context.verify(() -> {
                                    assertThat(resp.statusCode()).isEqualTo(200);
                                    context.completeNow();
                                });
                            }));
                }));
    }
}
