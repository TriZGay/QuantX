package io.futakotome.quantx.collect.tenant;

import io.quarkus.arc.Unremovable;
import io.quarkus.hibernate.orm.runtime.tenant.TenantResolver;
import io.vertx.ext.web.RoutingContext;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
@Unremovable
public class CustomTenantResolver implements TenantResolver {

    private static final Logger LOGGER = Logger.getLogger(CustomTenantResolver.class);
    private static final String tenantHeader = "X-TENANT-ID";
    @Inject
    RoutingContext routingContext;

    @Override
    public String getDefaultTenantId() {
        return "admin";
    }

    @Override
    public String resolveTenantId() {
        String tenantHeaderValue = routingContext.request().headers().get(tenantHeader);
        final String tenantId;
        if (tenantHeaderValue.equals("user1")) {
            tenantId = "user1";
        } else {
            tenantId = getDefaultTenantId();
        }
        LOGGER.infov("TenantId = {0}", tenantId);
        return tenantId;
    }
}
