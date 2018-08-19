package org.swat.jpa.multitenant;

import javax.persistence.Embeddable;

@Embeddable
public class DummyId {
    private String id;

    public String getId() {
        return id;
    }

    public DummyId setId(String id) {
        this.id = id;
        return this;
    }
}
