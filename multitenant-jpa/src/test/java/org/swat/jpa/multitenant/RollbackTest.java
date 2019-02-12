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
public class RollbackTest {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void rollback() {
        TenantContext.setTenantId("1");
        Department department = new Department();
        department.setId("1");
        try {
            departmentService.saveNRollback(department, true);
        } catch (RuntimeException e) {
            System.out.println("Rolled back");
        }
        List<Department> departments = departmentRepository.findAll();
        assertEquals(0, departments.size());

        departmentService.saveNRollback(department, false);
        departments = departmentRepository.findAll();
        assertEquals(1, departments.size());
    }
}