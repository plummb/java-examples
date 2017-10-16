package org.swat.drools;

import java.util.Random;

public class AllocationService {
    private static final Random RANDOM = new Random();
    public boolean isOk(Order order) {
        return "KL".contains(order.getName());
    }

    public boolean isOk(OrderItem order) {
        return "KL".contains(order.getName());
    }

    public String allocate(OrderItem item) {
        if(RANDOM.nextInt(100)>80){
            System.out.println("Allocation failed for item "+item.getName());
            return null;
        }
        String store = "HYD".equals(item.getStore()) ? "BLR" : "HYD";
        item.setStore(store);
        return store;
    }

    public void message(String message) {
        System.out.println(message);
    }
}
