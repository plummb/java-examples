package org.swat.jpa.multitenant;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;

import javax.persistence.*;

@Entity
@Table(name = "EMPLOYEE")
@Multitenant(MultitenantType.TABLE_PER_TENANT)
public class Employee {
    @EmbeddedId
    private DummyId id;

    public DummyId getId() {
        return id;
    }

    public Employee setId(DummyId id) {
        this.id = id;
        return this;
    }
}
