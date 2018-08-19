/*
 * Copyright © 2018 Swatantra Agrawal. All rights reserved.
 */

package org.swat.jpa.base;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import javax.persistence.EntityManager;

public class EntityManagerFactoryListener {
    private static final ThreadLocal<String> TENANT_TL = new ThreadLocal<>();

    public static void afterCreateEntityManager(EntityManager entityManager) {
        entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, "" + get());
    }

    public static void set(String tenantId) {
        TENANT_TL.set(tenantId);
    }

    private static String get() {
        return TENANT_TL.get();
    }
}
