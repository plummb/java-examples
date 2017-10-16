package org.swat.drools;

import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DroolsTest {
    public static void main(String[] args) throws
            Exception {
        DroolsTest droolsTest = new DroolsTest();
        droolsTest.executeDrools();
    }

    public void executeDrools() throws Exception {
        PackageBuilder packageBuilder = new PackageBuilder();
        String ruleFile = "org/swat/drools/allocation.drl";
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(ruleFile);
        Reader reader = new InputStreamReader(resourceAsStream);
        packageBuilder.addPackageFromDrl(reader);
        org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
        RuleBase ruleBase = RuleBaseFactory.newRuleBase();
        ruleBase.addPackage(rulesPackage);

        System.out.println("Firing one by one");
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order("Camera Only", "Hyderabad");
        order1.addItem(new OrderItem("Camera1", "Camera"));
        order1.addItem(new OrderItem("Camera2", "Camera"));
        orders.add(order1);
        fireRules(ruleBase, order1);

        Order order2 = new Order("One Camera", "Hyderabad");
        order2.addItem(new OrderItem("Camera3", "Camera"));
        order2.addItem(new OrderItem("Electronics1", "Electronics"));
        orders.add(order2);
        fireRules(ruleBase, order2);

        Order order3 = new Order("Multi Fridge", "Hyderabad");
        order3.addItem(new OrderItem("Camera4", "Camera"));
        order3.addItem(new OrderItem("Electronics2", "Electronics"));
        orders.add(order3);
        fireRules(ruleBase, order3);

        System.out.println("\n\nFiring all at one go");
        Collections.shuffle(orders);
        fireRules(ruleBase, orders.toArray(new Order[0]));
        System.out.println("\n\nFiring all at one go once again");
        Collections.shuffle(orders);
        fireRules(ruleBase, orders.toArray(new Order[0]));
    }

    private void fireRules(RuleBase ruleBase, Order... objects) {
        WorkingMemory workingMemory = ruleBase.newStatefulSession();
        for (Order object : objects) {
            object.setProcessed(false);
            for(OrderItem orderItem:object.getItems()){
                orderItem.setProcessed(false);
            }
            workingMemory.insert(object);
        }
        workingMemory.insert(new AllocationService());
        workingMemory.fireAllRules();
        System.out.println();
    }
}