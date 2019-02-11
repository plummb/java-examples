package org.swat.jpa.multitenant;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

import javax.persistence.*;

@Entity
@Table(name = "DEPARTMENT")
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = "COMPANY_ID")
@IdClass(DummyId.class)
public class Department {
    @Id
    @Column(name = "DEPARTMENT_ID")
    private String id;

    //This field is optional
    @Column(name = "COMPANY_ID", insertable = false, updatable = false)
    private String tenantId;

    public String getId() {
        return id;
    }

    public Department setId(String id) {
        this.id = id;
        return this;
    }
}
