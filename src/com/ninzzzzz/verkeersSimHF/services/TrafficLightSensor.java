package com.ninzzzzz.verkeersSimHF.services;

public class TrafficLightSensor {
    private final int sensorId;

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
            return Math.min(vehicleCount, 10);
        } else if (sensorId == 4) {
            return vehicleCount;
        } else {
            return Math.min(vehicleCount, 5);
        }
    }
}
