package org.swat.jpa.multitenant;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
@Multitenant(MultitenantType.TABLE_PER_TENANT)
@IdClass(DummyId.class)
public class Employee {
    @Id
    @Column(name = "EMPLOYEE_ID")
    private String id;

    public String getId() {
        return id;
    }

    public Employee setId(String id) {
        this.id = id;
        return this;
    }
}
