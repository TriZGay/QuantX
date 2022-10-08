package io.futakotome.quantx.collect.tenant;

import io.quarkus.arc.Unremovable;
import io.vertx.core.Handler;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.MultiTenantHandler;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;

@RequestScoped
@Unremovable
public class CustomTenantResolver implements MultiTenantHandler {

    private static final Logger LOGGER = Logger.getLogger(CustomTenantResolver.class);
//
//    @Inject
//    RoutingContext routingContext;
//
//    @Override
//    public String getDefaultTenantId() {
//        return "admin";
//    }
//
//    @Override
//    public String resolveTenantId() {
//        String path = routingContext.request().path();
//        final String tenantId;
//        if (path.startsWith("/user1")) {
//            tenantId = "user1";
//        } else {
//            tenantId = getDefaultTenantId();
//        }
//        LOGGER.debugv("TenantId = {0}", tenantId);
//        return tenantId;
//    }

    @Override
    public MultiTenantHandler addTenantHandler(String s, Handler<RoutingContext> handler) {
        return null;
    }

    @Override
    public MultiTenantHandler removeTenant(String s) {
        return null;
    }

    @Override
    public MultiTenantHandler addDefaultHandler(Handler<RoutingContext> handler) {
        return null;
    }

    @Override
    public void handle(RoutingContext routingContext) {

    }
}
