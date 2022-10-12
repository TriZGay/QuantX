import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class PlateEndpointTest {
    @Test
    public void testFindAll() {
        given().when()
                .get("/plates/")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreate() {
        given().when()
                .body(new JsonObject()
                        .put("name", "name")
                        .put("code", "code")
                        .put("plateId", "plateId").toString())
                .contentType("application/json")
                .post("/plates/")
                .then()
                .statusCode(201);
    }
}
