package org.swat.cassandra;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by swat
 * on 11/6/17.
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.swat.cassandra"})
@Ignore
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