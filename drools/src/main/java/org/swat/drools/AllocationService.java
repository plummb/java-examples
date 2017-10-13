package org.swat.drools;

public class AllocationService {
    public boolean isOk(Order order) {
        return "KL".contains(order.getName());
    }

    public boolean isOk(OrderItem order) {
        return "KL".contains(order.getName());
    }

    public String allocate(OrderItem item) {
        String store = "HYD".equals(item.getStore()) ? "BLR" : "HYD";
        item.setStore(store);
        return store;
    }

    public void message(String message) {
        System.out.println(message);
    }
}
