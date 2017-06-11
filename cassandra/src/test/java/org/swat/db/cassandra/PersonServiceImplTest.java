package org.swat.db.cassandra;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.swat.db.cassandra.Person;
import org.swat.db.cassandra.PersonServiceImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by swat
 * on 11/6/17.
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.swat.cassandra"})
public class PersonServiceImplTest {
    @Autowired
    private PersonServiceImpl personService;

    @Test
    public void findById() throws Exception {
        Person person = new Person();
        person.setName("Swat");
        person = personService.insert(person);
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Swat", person.getName());

        person = personService.findById(person.getId());
        assertNotNull(person);
        assertNotNull(person.getId());
        assertEquals("Swat", person.getName());
    }
}