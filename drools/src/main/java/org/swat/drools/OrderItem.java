package org.swat.drools;

import java.util.Arrays;
import java.util.List;

public class OrderItem extends DroolsState  implements Cloneable{
    private String name;
    private List<String> tags;
    private String store;

    public OrderItem() {
    }

    public OrderItem(String name, String... tags) {
        this.name = name;
        this.tags = Arrays.asList(tags);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    @Override
    protected OrderItem clone() throws CloneNotSupportedException {
        return (OrderItem) super.clone();
    }
}