/*
 * Copyright © 2018 Swatantra Agrawal. All rights reserved.
 */

package org.swat.jpa.base;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.swat.jpa.multitenant.TenantContext;

import javax.persistence.EntityManager;

public class EntityManagerFactoryListener {
    public static void afterCreateEntityManager(EntityManager entityManager) {
        entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, "" + TenantContext.getTenantId());
    }
}
