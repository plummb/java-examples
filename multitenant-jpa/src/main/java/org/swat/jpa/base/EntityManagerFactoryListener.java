/*
 * Copyright © 2018 Swatantra Agrawal. All rights reserved.
 */

package org.swat.jpa.base;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.swat.jpa.multitenant.TenantContext;

import javax.persistence.EntityManager;

/**
 * This Class is required to be created with same package and class name.
 */
public class EntityManagerFactoryListener {
    /**
     * This method is called by JPA Agent. The method signature has to be same.
     *
     * @param entityManager the entity manager
     */
    public static void afterCreateEntityManager(EntityManager entityManager) {
        //Business logic
        entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, "" + TenantContext.getTenantId());
    }
}
