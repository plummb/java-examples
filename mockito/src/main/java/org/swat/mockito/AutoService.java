package org.swat.mockito;

import org.springframework.stereotype.Component;

@Component
public class AutoService implements VehicleService {
    @Override
    public String type() {
        return "AUTO";
    }
}
