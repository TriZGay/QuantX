package io.trizgay.quantx.http;

import io.trizgay.quantx.domain.BizService;
import io.trizgay.quantx.http.controller.BizController;
import io.trizgay.quantx.http.handler.MonitorHandler;
import io.trizgay.quantx.utils.Config;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.openapi.RouterBuilder;
import io.vertx.serviceproxy.ServiceBinder;

import static io.trizgay.quantx.utils.Constants.CONFIG_PG_EVENT_BUS;
import static io.trizgay.quantx.utils.Constants.X_VERTX_EVENT_BUS;

public class HttpVerticle extends AbstractVerticle {
    private HttpServer httpServer;

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        initOpenApi();
        startHttpServer().onComplete(startPromise);
    }

    private void initOpenApi() {
        ServiceBinder serviceBinder = new ServiceBinder(vertx);
        BizService bizService = BizService.createProxy(vertx, CONFIG_PG_EVENT_BUS);
        serviceBinder.setAddress(X_VERTX_EVENT_BUS)
                .register(BizController.class, BizController.create(bizService));
    }

    private Future<Void> startHttpServer() {
        Router mainRouter = Router.router(vertx);
        return RouterBuilder.create(vertx, "openApi.yaml")
                .onFailure(Throwable::printStackTrace)
                .compose(routerBuilder -> {
                    routerBuilder.mountServicesFromExtensions();
                    Router subRouter = routerBuilder.createRouter();
                    mainRouter.route().handler(BodyHandler.create());
                    mainRouter.route().handler(MonitorHandler.create());
                    mainRouter.mountSubRouter(Config.localConfig().getServer().getPath(), subRouter);
                    httpServer = vertx.createHttpServer(
                            new HttpServerOptions()
                                    .setPort(Config.localConfig().getServer().getPort())
                                    .setHost(Config.localConfig().getServer().getHost())
                    ).requestHandler(mainRouter);
                    return httpServer.listen().mapEmpty();
                });
    }


    @Override
    public void stop(Promise<Void> stopPromise) throws Exception {
        super.stop(stopPromise);
    }
}
