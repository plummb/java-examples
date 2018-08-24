package org.swat.mongodb;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by swat
 * on 11/6/17.
 */
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ComponentScan(basePackages = {"org.swat.mongodb"})
public class PersonServiceImplTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PersonServiceImpl personService;

    @Test
    public void findById() throws Exception {
        TenantContext.setTenantId("tenant123");
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
        String collectionName = "tenant123_person";
        assertTrue(mongoTemplate.getCollectionNames().contains(collectionName));
    }

    @Test
    public void multiDbTest() throws Exception {
        Person person = new Person();
        person.setName("Swat");

        TenantContext.setTenantId("1");
        person = personService.insert(person);
        assertEquals(1, mongoTemplate.getCollectionNames().size());

        TenantContext.setTenantId("2");
        person = personService.insert(person);
        assertEquals(2, mongoTemplate.getCollectionNames().size());

        TenantContext.setTenantId("3");
        person = personService.insert(person);
        assertEquals(1, mongoTemplate.getCollectionNames().size());

        TenantContext.setTenantId("4");
        person = personService.insert(person);
        assertEquals(2, mongoTemplate.getCollectionNames().size());
    }
}