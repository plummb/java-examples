/*
 * Copyright © 2018 Go Live Technologies. All rights reserved.
 */

package org.swat.jpa.multitenant;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.JpaBaseConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.AbstractJpaVendorAdapter;
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.eclipse.persistence.config.PersistenceUnitProperties.CREATE_OR_EXTEND;

@Configuration
public class EclipseLinkJpaConfiguration extends JpaBaseConfiguration {
  protected EclipseLinkJpaConfiguration(DataSource dataSource, JpaProperties properties,
                                        ObjectProvider<JtaTransactionManager> jtaTransactionManager,
                                        ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
    super(dataSource, properties, jtaTransactionManager, transactionManagerCustomizers);
  }

  @Override
  protected AbstractJpaVendorAdapter createJpaVendorAdapter() {
    return new EclipseLinkJpaVendorAdapter();
  }

  @Override
  protected Map<String, Object> getVendorProperties() {
    HashMap<String, Object> map = new HashMap<>();
    map.put(PersistenceUnitProperties.WEAVING, "false");
    map.put(PersistenceUnitProperties.DDL_GENERATION, CREATE_OR_EXTEND);
    map.put("eclipselink.logging.level.sql", "FINE");
    return map;
  }
}