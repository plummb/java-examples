package org.swat.jpa.multitenant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@EntityScan("org.swat.jpa.multitenant")
@ComponentScan(basePackages = {"org.swat.jpa"})
public class MultiTenantTest {
    @Autowired
    private EmployeeRepository repository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void checkMultiTenant() {
        jdbcTemplate.execute("CREATE TABLE EMPLOYEE_1" +
                "(" +
                "   EMPLOYEE_ID varchar(255) PRIMARY KEY NOT NULL" +
                ")");
        jdbcTemplate.execute("CREATE TABLE EMPLOYEE_2" +
                "(" +
                "   EMPLOYEE_ID varchar(255) PRIMARY KEY NOT NULL" +
                ")");
        TenantContext.setTenantId("1");
        Employee employee = new Employee();
        employee.setId("1");
        repository.save(employee);

        TenantContext.setTenantId("2");
        employee = new Employee();
        employee.setId("2");
        repository.save(employee);

        List<Employee> employees = repository.findAll();
        assertEquals(1, employees.size());
        assertEquals("2", employees.get(0).getId());

        TenantContext.setTenantId("1");
        employees = repository.findAll();
        assertEquals(1, employees.size());
        assertEquals("1", employees.get(0).getId());
    }
}