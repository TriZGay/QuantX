package io.trizgay.quantx.domain;

import io.vertx.codegen.annotations.GenIgnore;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.Vertx;
import io.vertx.serviceproxy.ServiceProxyBuilder;

@ProxyGen
@VertxGen
public interface BizServiceDispatcher {

    @GenIgnore
    static BizServiceDispatcher createProxy(Vertx vertx, String address) {
        return new ServiceProxyBuilder(vertx)
                .setAddress(address)
                .build(BizServiceDispatcher.class);
    }
}
