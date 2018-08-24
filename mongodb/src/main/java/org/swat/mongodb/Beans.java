package org.swat.mongodb;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.MultiTenantMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by swat
 * on 11/6/17.
 */
@Configuration
@EnableMongoRepositories(basePackages = "org.swat.mongodb")
public class Beans {
    private static final String MONGO_DB_URL = "localhost";
    private static final String MONGO_DB_NAME = "embeded_db";
    private static Map<String, String> dbMap = new HashMap<>();

    static {
        dbMap.put("1", "dbA");
        dbMap.put("2", "dbA");
        dbMap.put("3", "dbB");
        dbMap.put("4", "dbB");
    }

    @Bean("mongoTemplate")
    public MongoTemplate getMongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
        mongo.setBindIp(MONGO_DB_URL);
        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MultiTenantMongoTemplate(mongoClient, MONGO_DB_NAME) {
            private final Map<String, MongoTemplate> templateMap = new HashMap<>();

            @Override
            protected MongoTemplate resolveMongoTemplate() {
                String dbName = dbMap.get(TenantContext.getTenantId());
                if (StringUtils.isBlank(dbName)) {
                    return super.getGlobalTemplate();
                }
                MongoTemplate tenantTemplate = templateMap.get(TenantContext.getTenantId());
                if (tenantTemplate == null) {
                    tenantTemplate = new MongoTemplate(mongoClient, dbName);
                    templateMap.put(TenantContext.getTenantId(), tenantTemplate);
                }
                return tenantTemplate;
            }
        };
        return mongoTemplate;
    }
}
