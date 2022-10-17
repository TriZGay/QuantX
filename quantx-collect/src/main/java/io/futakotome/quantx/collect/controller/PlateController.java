package io.futakotome.quantx.collect.controller;

import com.futu.openapi.pb.QotCommon;
import com.futu.openapi.pb.QotGetPlateSet;
import io.futakotome.quantx.collect.domain.plate.Plate;
import io.futakotome.quantx.collect.onboot.FutuQotService;
import io.quarkus.vertx.web.Body;
import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RouteBase;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.hibernate.reactive.mutiny.Mutiny;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import java.util.List;

@RouteBase(path = "/plates", produces = "application/json")
public class PlateController extends BaseController {
    private static final Logger LOGGER = Logger.getLogger(PlateController.class.getName());

    @Inject
    Mutiny.SessionFactory sessionFactory;

    @Inject
    FutuQotService futuQotService;

    @Route(methods = Route.HttpMethod.GET, path = "/sync")
    public void syncPlateData(RoutingContext routingContext) {
        QotGetPlateSet.C2S c2S = QotGetPlateSet.C2S.newBuilder()
                .setMarket(QotCommon.QotMarket.QotMarket_HK_Security_VALUE)
                .setPlateSetType(QotCommon.PlateSetType.PlateSetType_All_VALUE)
                .build();
        QotGetPlateSet.Request request = QotGetPlateSet.Request.newBuilder().setC2S(c2S).build();
        int seqNo = futuQotService.getQot().getPlateSet(request);
        routingContext.response().setStatusCode(200).end("succeed:" + seqNo);
    }

    @Route(methods = Route.HttpMethod.GET, path = "/")
    public Uni<List<Plate>> getAll() {
        return sessionFactory.withSession(session -> session.createNamedQuery(Plate.FIND_ALL, Plate.class)
                .getResultList());
    }

    @Route(methods = Route.HttpMethod.POST, path = "/")
    public Uni<Plate> create(@Body Plate plate, HttpServerResponse response) {
        if (plate == null || plate.getId() != null) {
            return Uni.createFrom().failure(new IllegalStateException("Plate id invalidly set on request"));
        }
        return sessionFactory.withTransaction((session, transaction) ->
                session.persist(plate)
                        .invoke(() -> response.setStatusCode(201)))
                .replaceWith(plate);
    }

    @Route(methods = Route.HttpMethod.PUT, path = "/:id")
    public Uni<Plate> update(@Body Plate plate, @Param Long id) {
        if (plate == null || plate.getName() == null) {
            return Uni.createFrom().failure(new IllegalArgumentException("Plate name was not set on request."));
        }
        return sessionFactory.withTransaction((session, transaction) -> session.find(Plate.class, id)
                .onItem().ifNotNull().invoke(entity -> entity.setName(plate.getName()))
                .onItem().ifNull().fail()
        );
    }

    @Route(methods = Route.HttpMethod.DELETE, path = "/:id")
    public Uni<Plate> delete(@Param Long id, HttpServerResponse httpServerResponse) {
        return sessionFactory.withTransaction((session, tx) -> session.find(Plate.class, id)
                .onItem().ifNotNull()
                .call(entity -> session.remove(entity)
                        .invoke(() -> httpServerResponse.setStatusCode(204)))
                .onItem().ifNull().fail());
    }

}
