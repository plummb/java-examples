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
@ComponentScan(basePackages = {"org.swat.jpa.multitenant"})
public class MultiTenantTest {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void perTable() {
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
        employeeRepository.save(employee);
        employeeRepository.getOne(new DummyId("1"));

        TenantContext.setTenantId("2");
        employee = new Employee();
        employee.setId("2");
        employeeRepository.save(employee);

        List<Employee> employees = employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals("2", employees.get(0).getId());

        TenantContext.setTenantId("1");
        employees = employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals("1", employees.get(0).getId());
    }
    @Test
    public void singleTable() {
        TenantContext.setTenantId("1");
        Department department = new Department();
        department.setId("1");
        departmentRepository.save(department);
        departmentRepository.getOne(new DummyId("1"));

        TenantContext.setTenantId("2");
        department = new Department();
        department.setId("2");
        departmentRepository.save(department);

        List<Department> departments = departmentRepository.findAll();
        assertEquals(1, departments.size());
        assertEquals("2", departments.get(0).getId());

        TenantContext.setTenantId("1");
        departments = departmentRepository.findAll();
        assertEquals(1, departments.size());
        assertEquals("1", departments.get(0).getId());
    }
}
