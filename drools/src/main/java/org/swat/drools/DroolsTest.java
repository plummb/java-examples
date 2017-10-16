package org.swat.drools;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.definition.KiePackage;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DroolsTest {
    public static void main(String[] args) throws
            Exception {
        DroolsTest droolsTest = new DroolsTest();
        droolsTest.executeDrools();
    }

    public void executeDrools() throws Exception {
        InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

        String ruleFile = "org/swat/drools/allocation.drl";
        kbuilder.add(ResourceFactory.newClassPathResource(ruleFile,
                DroolsTest.class),
                ResourceType.DRL);

        Collection<KiePackage> pkgs = kbuilder.getKnowledgePackages();
        kbase.addPackages(pkgs);

        System.out.println("Firing one by one");
        List<Order> orders = new ArrayList<>();
        Order order1 = new Order("Camera Only", "Hyderabad");
        order1.addItem(new OrderItem("Camera1", "Camera"));
        order1.addItem(new OrderItem("Camera2", "Camera"));
        orders.add(order1);
        fireRules(kbase, order1);

        Order order2 = new Order("One Camera", "Hyderabad");
        order2.addItem(new OrderItem("Camera3", "Camera"));
        order2.addItem(new OrderItem("Electronics1", "Electronics"));
        orders.add(order2);
        fireRules(kbase, order2);

        Order order3 = new Order("Multi Fridge", "Hyderabad");
        order3.addItem(new OrderItem("Camera4", "Camera"));
        order3.addItem(new OrderItem("Electronics2", "Electronics"));
        orders.add(order3);
        fireRules(kbase, order3);

        System.out.println("\n\nFiring all at one go");
        Collections.shuffle(orders);
        fireRules(kbase, orders.toArray(new Order[0]));
        System.out.println("\n\nFiring all at one go once again");
        Collections.shuffle(orders);
        fireRules(kbase, orders.toArray(new Order[0]));
    }

    private void fireRules(InternalKnowledgeBase kbase, Order... objects) {
        KieSession kieSession = kbase.newKieSession();
        for (Order object : objects) {
            object.setProcessed(false);
            for (OrderItem orderItem : object.getItems()) {
                orderItem.setProcessed(false);
            }
            kieSession.insert(object);
        }
        kieSession.insert(new AllocationService());
        kieSession.fireAllRules();
        System.out.println();
    }
}