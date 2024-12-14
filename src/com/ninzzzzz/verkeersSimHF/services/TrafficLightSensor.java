package com.ninzzzzz.verkeersSimHF.services;

public class TrafficLightSensor {
    private final int sensorId;

    public TrafficLightSensor(int sensorId) {
        this.sensorId = sensorId;
    }

    public boolean shouldSkipGreenLight(int vehicleCount) {
        return sensorId == 1 && vehicleCount == 0;
    } // wegdek wordt geskipt als er geen vehicles zijn

    public boolean shouldExtendGreenLight(int vehicleCount) {
        return (sensorId == 2 || sensorId == 3) && vehicleCount > 10;
    } // als er meer dan 10 vehicles op wegdek zijn duurt groenlicht langer(langer dan default)

    public boolean shouldShortenGreenLight(int vehicleCount) {
        return sensorId == 4 && vehicleCount < 5;
    } // als er minder dan 5 vehicles op wegdek zijn duurt groenlicht korter

    public int vehiclesToAllow(int vehicleCount) {
        if (sensorId == 2 || sensorId == 3) {
            return Math.min(vehicleCount, 10); // 10 vehicles kunnen wegrijden
        } else if (sensorId == 4) {
            return vehicleCount; // Alle vehicles kunnen wegrijden
        } else {
            return Math.min(vehicleCount, 5); // Default: 5 vehicles rijden weg
        }
    }
}
