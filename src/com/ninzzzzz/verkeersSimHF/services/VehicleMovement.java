package com.ninzzzzz.verkeersSimHF.services;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;

public class VehicleMovement {
    private Vehicle vehicle;
    private String roadName;

    public VehicleMovement(Vehicle vehicle, String roadName) {
        this.vehicle = vehicle;
        this.roadName = roadName;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getRoadName() {
        return roadName;
    }
}
