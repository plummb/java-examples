package org.swat.jpa.multitenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public Department saveNRollback(Department department, boolean rollback) {
        department = departmentRepository.save(department);
        if (rollback)
            throw new RuntimeException("Thrown intentionally to rollback");
        return department;
    }
}
