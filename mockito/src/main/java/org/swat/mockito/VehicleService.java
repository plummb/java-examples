package org.swat.mockito;

public interface VehicleService {
    String type();

    default String process() {
        return type();
    }
}
