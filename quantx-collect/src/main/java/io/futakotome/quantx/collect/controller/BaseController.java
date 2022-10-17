package io.futakotome.quantx.collect.controller;

import io.quarkus.vertx.web.Route;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.jboss.logging.Logger;

import java.util.NoSuchElementException;

public class BaseController {
    private static final Logger LOGGER = Logger.getLogger(BaseController.class.getName());

    @Route(path = "/*", type = Route.HandlerType.FAILURE)
    public void error(RoutingContext routingContext) {
        Throwable throwable = routingContext.failure();
        if (throwable != null) {
            LOGGER.error("Failed to handle request ", throwable);
            int status = routingContext.statusCode();
            String chunk = "";
            if (throwable instanceof NoSuchElementException) {
                status = 404;
            } else if (throwable instanceof IllegalArgumentException) {
                status = 422;
                chunk = new JsonObject().put("code", status)
                        .put("exceptionType", throwable.getClass().getName())
                        .put("error", throwable.getMessage())
                        .encode();
            }
            routingContext.response().setStatusCode(status).end(chunk);
        } else {
            routingContext.next();
        }
    }
}
