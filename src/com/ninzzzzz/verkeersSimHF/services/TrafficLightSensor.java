package com.ninzzzzz.verkeersSimHF.services;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;

public class TrafficLightSensor {
    private int sensorId;

    public TrafficLightSensor(int sensorId) {
        this.sensorId = sensorId;
    }

    public boolean shouldSkipGreenLight(int vehicleCount) {
        return sensorId == 1 && vehicleCount == 0;
    }

    public boolean shouldExtendGreenLight(int vehicleCount) {
        return (sensorId == 2 || sensorId == 3) && vehicleCount > 10;
    }

    public boolean shouldShortenGreenLight(int vehicleCount) {
        return sensorId == 4 && vehicleCount < 5;
    }

    public int vehiclesToAllow(int vehicleCount) {
        if (sensorId == 2 || sensorId == 3) {
            return Math.min(vehicleCount, 10); // Allow up to 10 vehicles
        } else if (sensorId == 4) {
            return vehicleCount; // Allow all vehicles
        } else {
            return Math.min(vehicleCount, 5); // Default: allow up to 5 vehicles
        }
    }
}
