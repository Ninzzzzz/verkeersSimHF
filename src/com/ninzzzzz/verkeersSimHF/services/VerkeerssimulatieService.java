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
    private MyStack<VehicleMovement> vehicleMovementStack = new MyStack<>();
    private int totalCycles = 0;

    public VerkeerssimulatieService() {
        this.northSensor = new TrafficLightSensor(4); // North
        this.southSensor = new TrafficLightSensor(2); // South
        this.eastSensor = new TrafficLightSensor(1);  // East
        this.westSensor = new TrafficLightSensor(3);  // West
    }

    public void calculateTrafficLightCycles(Wegdek[] roads) {
        playbackOrder = new MyLinkedList<>(); // Reset playback order
        int maxVehiclesPerCycle = 5;
        boolean hasMoreVehicles;

        do {
            hasMoreVehicles = false;
            for (Wegdek road : roads) {
                if (!road.isWegdekEmpty()) {
                    processTrafficLightCycle(road, getSensorForRoad(road.getNaam()), maxVehiclesPerCycle);
                    playbackOrder.add(road); // Record the order of roads processed
                    hasMoreVehicles = true;
                    System.out.println(); // Add space between roads for readability
                }
            }
            totalCycles++;
        } while (hasMoreVehicles);

        System.out.println("Total Cycles: " + totalCycles);
    }

    private void processTrafficLightCycle(Wegdek road, TrafficLightSensor sensor, int maxVehiclesPerCycle) {
        int vehicleCount = road.getVehicleCount();

        if (sensor.shouldSkipGreenLight(vehicleCount)) {
            System.out.println(road.getNaam() + ": Skipping green light.");
            return;
        }

        int vehiclesToProcess = sensor.vehiclesToAllow(vehicleCount);
        vehiclesToProcess = Math.min(vehiclesToProcess, maxVehiclesPerCycle); // Enforce max 5 vehicles per cycle

        MyStack<Vehicle> tempStack = new MyStack<>();
        MyStack<Vehicle> normalVehicleStack = new MyStack<>();

        System.out.println(road.getNaam() + ": Green light for " + vehiclesToProcess + " vehicles.");

        // Process higher priority vehicles first (police, fire truck, ambulance)
        for (int i = 0; i < vehiclesToProcess && !road.isWegdekEmpty(); i++) {
            Vehicle vehicle = road.peekNextVehicle();  // Peek to check the vehicle's priority
            if (vehicle.getPriority() > 0) {
                vehicle = road.removeVehicleFromWegdek();
                tempStack.push(vehicle);  // Push higher priority vehicle to temp stack
                printVehicleMovement(vehicle, road.getNaam(), i + 1);
            } else {
                normalVehicleStack.push(road.removeVehicleFromWegdek());  // Collect regular vehicles for later
            }
        }

        // Process remaining regular vehicles (if any)
        while (!normalVehicleStack.isEmpty() && vehiclesToProcess-- > 0) {
            Vehicle vehicle = normalVehicleStack.pop();
            tempStack.push(vehicle);
            printVehicleMovement(vehicle, road.getNaam(), vehiclesToProcess);
        }

        // Save the vehicles along with their roads for reverse playback
        while (!tempStack.isEmpty()) {
            vehicleMovementStack.push(new VehicleMovement(tempStack.pop(), road.getNaam()));  // Save vehicle and road info
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

    // Reverse playback
    public void reversePlayback() {
        System.out.println("Reverse Playback:");
        while (!vehicleMovementStack.isEmpty()) {
            VehicleMovement movement = vehicleMovementStack.pop();
            Vehicle vehicle = movement.getVehicle();
            String roadName = movement.getRoadName();
            String vehicleType = getVehicleType(vehicle.getPriority());
            if (vehicleType.equals("Regular")) {
                System.out.println("Vehicle " + vehicle.getId() + " drives back to " + roadName + ".");
            } else {
                System.out.println(vehicleType + " " + vehicle.getId() + " drives back to " + roadName + ".");
            }
        }
    }
}
