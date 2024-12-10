package com.ninzzzzz.verkeersSimHF.services;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;

public class VehicleMovement {
    private final Vehicle vehicle;
    private final String roadName;
    private final int followNumber;

    public VehicleMovement(Vehicle vehicle, String roadName, int followNumber) {
        this.vehicle = vehicle;
        this.roadName = roadName;
        this.followNumber = followNumber;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public String getRoadName() {
        return roadName;
    }

    public int getFollowNumber() {
        return followNumber;
    }
}
