package io.futakotome.quantx.collect.controller;

import io.futakotome.quantx.collect.domain.ipo.Ipo;
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
import javax.resource.NotSupportedException;
import java.util.List;
import java.util.NoSuchElementException;

@RouteBase(path = "/ipo", produces = "application/json")
public class IPOController {
    private static final Logger LOGGER = Logger.getLogger(IPOController.class.getName());

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Route(methods = Route.HttpMethod.GET, path = "/")
    public Uni<List<Ipo>> getAll() {
        return sessionFactory.withSession((session) ->
                session.createNamedQuery(Ipo.FIND_ALL, Ipo.class)
                        .getResultList());
    }

    @Route(methods = Route.HttpMethod.POST, path = "/")
    public Uni<Ipo> create(@Body Ipo ipo, HttpServerResponse httpServerResponse) {
        return Uni.createFrom().failure(new NotSupportedException());
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
