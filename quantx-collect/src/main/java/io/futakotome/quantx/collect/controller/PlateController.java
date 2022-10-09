package io.futakotome.quantx.collect.controller;

import io.futakotome.quantx.collect.domain.Plate;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.hibernate.reactive.mutiny.Mutiny;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.NoSuchElementException;

@RouteBase(produces = "application/json")
public class PlateController {
    private static final Logger LOGGER = Logger.getLogger(PlateController.class.getName());

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Route(methods = Route.HttpMethod.GET, path = "/plates")
    public Uni<List<Plate>> getAllAdmin() {
        return sessionFactory.withSession(session ->
                session.createNamedQuery(Plate.FIND_ALL, Plate.class)
                        .getResultList());
    }

//    @Route
//    public Uni<List<Plate>> getAllUser1() {
//
//    }
//
//    public List<Plate> fetchAll() {
//        return entityManager.createNamedQuery(Plate.FIND_ALL, Plate.class)
//                .getResultList();
//    }

    @Route(methods = Route.HttpMethod.POST, path = "/plates")
    public Uni<Plate> create(@Body Plate plate, HttpServerResponse response) {
        if (plate == null || plate.getId() != null) {
            return Uni.createFrom().failure(new IllegalStateException("Plate id invalidly set on request"));
        }
        return sessionFactory.withTransaction((session, transaction) -> session.persist(plate))
                .invoke(() -> response.setStatusCode(201))
                .replaceWith(plate);
    }

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
