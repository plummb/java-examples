package org.swat.mockito;

import org.springframework.stereotype.Component;

@Component
public class CarService implements VehicleService {
    @Override
    public String type() {
        return "CAR";
    }
}
