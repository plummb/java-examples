package org.swat.mongodb;

public class TenantContext {
    private static String tenantId;

    public static String getTenantId() {
        return tenantId;
    }

    public static void setTenantId(String tenantId) {
        TenantContext.tenantId = tenantId;
    }
}
