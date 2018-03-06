package org.swat.mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VehicleDelegator {
    private final Map<String, VehicleService> MAP = new HashMap<>();
    private final List<VehicleService> services;

    @Autowired
    public VehicleDelegator(List<VehicleService> services) {
        this.services = services;
    }

    public VehicleService getService(String type) {
        if (MAP.isEmpty())
            for (VehicleService service : services) {
                MAP.put(service.type(), service);
            }
        return MAP.get(type);
    }

    public String process(String type) {
        VehicleService service = getService(type);
        return service.process();
    }
}
