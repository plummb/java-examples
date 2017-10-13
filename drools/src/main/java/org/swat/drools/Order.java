package org.swat.drools;

import java.util.ArrayList;
import java.util.List;

public class Order extends DroolsState implements Cloneable{
    private String name;
    private String location;
    private List<OrderItem> items = new ArrayList<>();

    public Order() {
    }

    public Order(String name, String location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        if (items != null)
            this.items = items;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    @Override
    public Order clone() throws CloneNotSupportedException {
        Order order = (Order) super.clone();
        List<OrderItem> items = new ArrayList<>();
        for (OrderItem orderItem : this.getItems()) {
            items.add(orderItem.clone());
        }
        order.setItems(items);
        return order;
    }
}