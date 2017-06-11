package org.swat.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;

/**
 * Created by swat
 * on 11/6/17.
 */
public interface PersonRepository extends CassandraRepository<Person> {
}
