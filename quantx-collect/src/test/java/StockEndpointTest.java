import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class StockEndpointTest {
    @Test
    public void testFindAll() {
        given().when()
                .get("/stock/")
                .then()
                .statusCode(200);
    }

    @Test
    public void testCreate() {
        given().when()
                .body(new JsonObject()
                        .put("name", "name")
                        .put("code", "code")
                        .put("lotSize", 20)
                        .put("stockType", "name")
                        .put("stockChildType", "name")
                        .put("stockOwner", "name")
                        .put("optionType", "name")
                        .put("strikeTime", "name")
                        .put("strikePrice", 20D)
                        .put("suspension", 1)
                        .put("listingDate", "name")
                        .put("stockId", 20L)
                        .put("delisting", 1)
                        .put("indexOptionType", "name")
                        .put("mainContract", 1)
                        .put("lastTradeTime", "name")
                        .put("exchangeType", "name")
                        .toString())
                .contentType("application/json")
                .post("/stock/")
                .then()
                .statusCode(201);

    }

    @Test
    public void testUpdate() {
        given().when()
                .body(new JsonObject()
                        .put("name", "update2")
                        .toString())
                .contentType("application/json")
                .put("/stock/1")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDelete() {
        given().when()
                .delete("/stock/1")
                .then()
                .statusCode(204);
    }
}
