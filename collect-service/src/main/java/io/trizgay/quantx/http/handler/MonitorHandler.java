package io.trizgay.quantx.http.handler;

import io.trizgay.quantx.utils.Log;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;


public interface MonitorHandler extends Handler<RoutingContext> {
    static MonitorHandler create() {
        return routingContext -> {
            JsonObject body = routingContext.getBodyAsJson();
            String query = routingContext.request().query();
            String header = routingContext.request().headers().toString();
            Log.info("请求INCOMING:" + routingContext.request().absoluteURI() + "[" + routingContext.request().method().toString() + "]");
            Log.info("请求头:" + (header == null ? "" : header));
            Log.info("请求参数:" + (query == null ? "" : query));
            Log.info("请求体:" + (body == null ? "" : body.toString()));
            routingContext.next();
        };
    }
}
