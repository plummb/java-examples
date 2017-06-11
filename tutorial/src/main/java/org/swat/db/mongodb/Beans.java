package org.swat.db.mongodb;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by swat
 * on 11/6/17.
 */
@Configuration
@EnableMongoRepositories(basePackages = "org.swat.db.mongodb")
public class Beans {
    @Bean("mongoTemplate")
    public MongoTemplate getMongoTemplate() {
        //The settings should be externalized. Also lot of parameters need to be set.
        Mongo mongo = new MongoClient("127.0.0.1");
        return new MongoTemplate(mongo, "swat");
    }
}
