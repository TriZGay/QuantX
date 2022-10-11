//package io.futakotome.quantx.collect.tenant;
//
//import io.quarkus.arc.Unremovable;
//import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
//import io.vertx.ext.web.RoutingContext;
//import org.jboss.logging.Logger;
//
//import javax.enterprise.context.RequestScoped;
//import javax.inject.Inject;
//
//@RequestScoped
//@Unremovable
//public class CustomTenantResolver implements TenantResolver {
//
//    private static final Logger LOGGER = Logger.getLogger(CustomTenantResolver.class);
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
//}
