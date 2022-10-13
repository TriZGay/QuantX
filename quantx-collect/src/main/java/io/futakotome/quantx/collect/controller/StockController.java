package io.futakotome.quantx.collect.controller;

import io.futakotome.quantx.collect.domain.Stock;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.hibernate.reactive.mutiny.Mutiny;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RouteBase(path = "/stock", produces = "application/json")
public class StockController {
    private static final Logger LOGGER = Logger.getLogger(StockController.class.getName());

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Route(methods = Route.HttpMethod.GET, path = "/")
    public Uni<List<Stock>> getAll() {
        return sessionFactory.withSession(session -> session.createNamedQuery(Stock.FIND_ALL, Stock.class)
                .getResultList());
    }

    @Route(methods = Route.HttpMethod.POST, path = "/")
    public Uni<Stock> create(@Body Stock stock, HttpServerResponse httpServerResponse) {
        if (stock == null || stock.getId() != null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Stock id invalidly set on request."));
        }
        return sessionFactory.withTransaction((session, transaction) -> session.createNamedQuery(Stock.INSERT_ONE)
                .setParameter(1, stock.getName())
                .setParameter(2, stock.getCode())
                .setParameter(3, stock.getLotSize())
                .setParameter(4, stock.getStockType())
                .setParameter(5, stock.getStockChildType())
                .setParameter(6, stock.getStockOwner())
                .setParameter(7, stock.getOptionType())
                .setParameter(8, stock.getStrikeTime())
                .setParameter(9, stock.getStrikePrice())
                .setParameter(10, stock.isSuspension())
                .setParameter(11, stock.getListingDate())
                .setParameter(12, stock.getStockId())
                .setParameter(13, stock.isDelisting())
                .setParameter(14, stock.getIndexOptionType())
                .setParameter(15, stock.isMainContract())
                .setParameter(16, stock.getLastTradeTime())
                .setParameter(17, stock.getExchangeType())
                .executeUpdate()
                .invoke(() -> httpServerResponse.setStatusCode(201))
                .replaceWith(stock));
    }

    @Route(methods = Route.HttpMethod.PUT, path = "/:id")
    public Uni<Stock> update(@Body Stock stock, @Param Long id) {
        if (stock == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Stock update parameter was not set on request."));
        }
        //TODO 各字段判空
        return sessionFactory.withTransaction((session, transaction) -> session.find(Stock.class, id)
                .onItem().ifNotNull().invoke(entity -> {
                    entity.setName(stock.getName());
                    entity.setCode(stock.getCode());
                    entity.setLotSize(stock.getLotSize());
                    entity.setStockType(stock.getStockType());
                    entity.setStockChildType(stock.getStockChildType());
                    entity.setStockOwner(stock.getStockOwner());
                    entity.setOptionType(stock.getOptionType());
                    entity.setStrikeTime(stock.getStrikeTime());
                    entity.setStrikePrice(stock.getStrikePrice());
                    entity.setSuspension(stock.isSuspension());
                    entity.setListingDate(stock.getListingDate());
                    entity.setStockId(stock.getStockId());
                    entity.setDelisting(stock.isDelisting());
                    entity.setIndexOptionType(stock.getIndexOptionType());
                    entity.setMainContract(stock.isMainContract());
                    entity.setLastTradeTime(stock.getLastTradeTime());
                    entity.setExchangeType(stock.getExchangeType());
                    entity.setModifyDate(LocalDateTime.now());
                })
                .onItem().ifNull().fail());
    }

    @Route(methods = Route.HttpMethod.DELETE, path = "/:id")
    public Uni<Stock> delete(@Param Long id, HttpServerResponse httpServerResponse) {
        return sessionFactory.withTransaction((session, transaction) -> session.find(Stock.class, id)
                .onItem().ifNotNull()
                .call(entity -> session.remove(entity)
                        .invoke(() -> httpServerResponse.setStatusCode(204)))
                .onItem().ifNull().fail());
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
