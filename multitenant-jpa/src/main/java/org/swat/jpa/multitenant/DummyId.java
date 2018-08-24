package org.swat.jpa.multitenant;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DummyId implements Serializable {
    @Column(name = "EMPLOYEE_ID")
    private String id;

    public DummyId() {
    }

    public DummyId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public DummyId setId(String id) {
        this.id = id;
        return this;
    }
}
