package org.swat.jpa.multitenant;

public class TenantContext {
    private static final ThreadLocal<String> TENANT_TL = new ThreadLocal<>();

    public static String getTenantId() {
        return TENANT_TL.get();
    }

    public static void setTenantId(String tenantId) {
        TENANT_TL.set(tenantId);
    }
}
