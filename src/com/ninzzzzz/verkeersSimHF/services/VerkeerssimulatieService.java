package com.ninzzzzz.verkeersSimHF.services;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;
import com.ninzzzzz.verkeersSimHF.implementations.MyStack;
import com.ninzzzzz.verkeersSimHF.implementations.MyLinkedList;

public class VerkeerssimulatieService {
    private TrafficLightSensor northSensor;
    private TrafficLightSensor southSensor;
    private TrafficLightSensor eastSensor;
    private TrafficLightSensor westSensor;

    private MyLinkedList<Wegdek> playbackOrder = new MyLinkedList<>();
    private MyStack<Vehicle> stack = new MyStack<>();

    public VerkeerssimulatieService() {
        this.northSensor = new TrafficLightSensor(4); // North
        this.southSensor = new TrafficLightSensor(2); // South
        this.eastSensor = new TrafficLightSensor(1);  // East
        this.westSensor = new TrafficLightSensor(3);  // West
    }

    public void calculateTrafficLightCycles(Wegdek[] roads) {
        playbackOrder = new MyLinkedList<>(); // Reset playback order
        for (Wegdek road : roads) {
            processTrafficLightCycle(road, getSensorForRoad(road.getNaam()));
            playbackOrder.add(road); // Record the order of roads processed
        }
    }

    private void processTrafficLightCycle(Wegdek road, TrafficLightSensor sensor) {
        int vehicleCount = road.getVehicleCount();

        if (sensor.shouldSkipGreenLight(vehicleCount)) {
            System.out.println(road.getNaam() + ": Skipping green light.");
            return;
        }

        int maxVehicles = sensor.vehiclesToAllow(vehicleCount);
        MyStack<Vehicle> tempStack = new MyStack<>();

        System.out.println(road.getNaam() + ": Green light for " + maxVehicles + " vehicles.");

        for (int i = 0; i < maxVehicles; i++) {
            Vehicle vehicle = road.removeVehicleFromWegdek();
            tempStack.push(vehicle);
            printVehicleMovement(vehicle, road.getNaam(), i + 1);
        }

        while (!tempStack.isEmpty()) {
            stack.push(tempStack.pop()); // Save for reverse playback
        }
    }

    private TrafficLightSensor getSensorForRoad(String roadName) {
        switch (roadName) {
            case "North":
                return northSensor;
            case "South":
                return southSensor;
            case "East":
                return eastSensor;
            case "West":
                return westSensor;
            default:
                return null;
        }
    }

    private void printVehicleMovement(Vehicle vehicle, String roadName, int vehicleNumber) {
        String vehicleType = getVehicleType(vehicle.getPriority());
        if (vehicleType.equals("Regular")) {
            System.out.println("Vehicle " + vehicleNumber + " on " + roadName + " (" + vehicle.getId() + ") drives away.");
        } else {
            System.out.println(vehicleType + " on " + roadName + " (" + vehicle.getId() + ") drives away.");
        }
    }

    private String getVehicleType(int priority) {
        switch (priority) {
            case 1:
                return "Police";
            case 2:
                return "Fire Truck";
            case 3:
                return "Ambulance";
            default:
                return "Regular";
        }
    }

    public void reversePlayback() {
        System.out.println("Reverse Playback:");
        while (!stack.isEmpty()) {
            Vehicle vehicle = stack.pop();
            String vehicleType = getVehicleType(vehicle.getPriority());
            if (vehicleType.equals("Regular")) {
                System.out.println(vehicleType + " on " + vehicle.getId() + " drives away.");
            } else {
                System.out.println(vehicleType + " on " + vehicle.getId() + " drives away.");
            }
        }
    }
}
