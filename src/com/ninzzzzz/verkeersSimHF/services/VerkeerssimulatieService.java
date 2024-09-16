package com.ninzzzzz.verkeersSimHF.services;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;
import com.ninzzzzz.verkeersSimHF.implementations.MyStack;
import com.ninzzzzz.verkeersSimHF.implementations.MyQueue;
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

        MyStack<Vehicle> priorityVehicleStack = new MyStack<>(); // Stack for priority vehicles (LIFO)
        MyQueue<Vehicle> regularVehicleQueue = new MyQueue<>();  // Queue for regular vehicles (FIFO)

        System.out.println(road.getNaam() + ": Green light for " + vehiclesToProcess + " vehicles.");

        // Separate priority and regular vehicles
        for (int i = 0; i < vehiclesToProcess && !road.isWegdekEmpty(); i++) {
            Vehicle vehicle = road.peekNextVehicle(); // Peek to check the vehicle's priority
            if (vehicle.getPriority() > 0) {
                vehicle = road.removeVehicleFromWegdek();
                priorityVehicleStack.push(vehicle); // Collect high-priority vehicles in a stack
            } else {
                regularVehicleQueue.enqueue(road.removeVehicleFromWegdek()); // Collect regular vehicles in a queue
            }
        }

        // Process high-priority vehicles first (LIFO)
        while (!priorityVehicleStack.isEmpty()) {
            Vehicle vehicle = priorityVehicleStack.pop();
            printVehicleMovement(vehicle, road.getNaam());
            vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam())); // Save for reverse playback
        }

        // Process remaining regular vehicles in FIFO order
        int vehicleNumber = 1; // Start numbering regular vehicles correctly
        while (!regularVehicleQueue.isEmpty() && vehiclesToProcess-- > 0) {
            Vehicle vehicle = regularVehicleQueue.dequeue();
            printVehicleMovement(vehicle, road.getNaam(), vehicleNumber);
            vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam())); // Save for reverse playback
            vehicleNumber++;
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

    private void printVehicleMovement(Vehicle vehicle, String roadName) {
        String vehicleType = getVehicleType(vehicle.getPriority());
        if (vehicleType.equals("Regular")) {
            System.out.println("Vehicle on " + roadName + " (" + vehicle.getId() + ") drives away.");
        } else {
            System.out.println(vehicleType + " on " + roadName + " (" + vehicle.getId() + ") drives away.");
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
