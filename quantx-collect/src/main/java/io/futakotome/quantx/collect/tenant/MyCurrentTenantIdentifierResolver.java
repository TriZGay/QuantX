package io.futakotome.quantx.collect.tenant;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

public class MyCurrentTenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    public enum Tenant {
        DEFAULT("admin"),
        TENANT_1("user1");

        private final String name;

        Tenant(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private Tenant tenantId = Tenant.DEFAULT;

    public void setTenantIdentifier(Tenant tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        return tenantId.name();
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
