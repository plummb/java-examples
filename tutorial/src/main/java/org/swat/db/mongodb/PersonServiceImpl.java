package org.swat.db.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by swat
 * on 11/6/17.
 */
@Service
public class PersonServiceImpl {
    @Autowired
    private PersonRepository personRepository;

    public Person insert(Person person){
        person.setId(UUID.randomUUID().toString());
        personRepository.insert(person);
        return person;
    }

    public Person findById(String id) {
        return personRepository.findOne(id);
    }
}
