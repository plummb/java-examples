package org.swat.db.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by swat
 * on 11/6/17.
 */
public interface PersonRepository extends MongoRepository<Person, String> {
}
