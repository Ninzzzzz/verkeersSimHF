package com.ninzzzzz.verkeersSimHF.services;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;

public class TrafficLightSensor {
    private int id;

    public TrafficLightSensor(int id) {
        this.id = id;
    }

    public boolean checkCondition(Wegdek road) {
        int vehicleCount = 0;
        Vehicle nextVehicle = road.peekNextVehicle();

        while (nextVehicle != null) {
            vehicleCount++;
            road.removeVehicleFromWegdek(); // Temporarily remove to count, re-add later if needed
            nextVehicle = road.peekNextVehicle();
        }

        // Re-add vehicles to restore original state
        for (int i = 0; i < vehicleCount; i++) {
            road.addVehicleToWegdek(new Vehicle("TempID" + i, 0)); // Dummy vehicle, replace with actual ones
        }

        switch (id) {
            case 1:
                return vehicleCount > 0;
            case 2:
                return vehicleCount > 10;
            case 3:
                return vehicleCount > 10;
            case 4:
                return vehicleCount < 5;
            default:
                return false;
        }
    }
}
