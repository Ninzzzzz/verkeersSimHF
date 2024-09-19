package com.ninzzzzz.verkeersSimHF.services;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;
import com.ninzzzzz.verkeersSimHF.implementations.MyStack;
import com.ninzzzzz.verkeersSimHF.implementations.MyQueue;
import com.ninzzzzz.verkeersSimHF.implementations.MyLinkedList;

public class VerkeerssimulatieService {
    private MyLinkedList<Wegdek> playbackOrder = new MyLinkedList<>();
    private MyStack<VehicleMovement> vehicleMovementStack = new MyStack<>();
    private int totalCycles = 0;

    public void calculateTrafficLightCycles(Wegdek[] roads) {
        playbackOrder = new MyLinkedList<>(); // Reset playback order
        int maxVehiclesPerCycle = 5;
        boolean hasMoreVehicles;

        // **Process all high-priority vehicles (Police, Ambulance, Fire Truck) first**
        processAllPriorityVehicles(roads);

        // **Then continue processing regular vehicles**
        do {
            hasMoreVehicles = false;
            for (Wegdek road : roads) {
                if (!road.isWegdekEmpty()) {
                    processRegularVehicles(road, maxVehiclesPerCycle);
                    playbackOrder.add(road); // Record the order of roads processed
                    hasMoreVehicles = true;
                    System.out.println(); // Add space between roads for readability
                }
            }
            totalCycles++;
        } while (hasMoreVehicles);

        System.out.println("Total Cycles: " + totalCycles);
    }

    private void processAllPriorityVehicles(Wegdek[] roads) {
        for (Wegdek road : roads) {
            MyQueue<Vehicle> tempQueue = new MyQueue<>();
            MyQueue<Vehicle> priorityQueue = new MyQueue<>();  // Priority vehicles

            // Separate high-priority and regular vehicles
            while (!road.isWegdekEmpty()) {
                Vehicle vehicle = road.peekNextVehicle();
                if (vehicle.getPriority() > 0) {
                    priorityQueue.enqueue(road.removeVehicleFromWegdek());  // Collect priority vehicles
                } else {
                    tempQueue.enqueue(road.removeVehicleFromWegdek());  // Collect regular vehicles in original order
                }
            }

            // Process priority vehicles
            processPriorityVehicles(priorityQueue, road);

            // Restore the regular vehicles back to the road
            road.setVehicleQueue(tempQueue);  // Keep the FIFO order of regular vehicles intact
        }
    }

    private void processPriorityVehicles(MyQueue<Vehicle> priorityQueue, Wegdek road) {
        String[] priorityOrder = {"Police", "Ambulance", "Fire Truck"};

        for (String priorityType : priorityOrder) {
            MyQueue<Vehicle> tempQueue = new MyQueue<>();
            while (!priorityQueue.isEmpty()) {
                Vehicle vehicle = priorityQueue.dequeue();
                String vehicleType = getVehicleType(vehicle.getPriority());
                if (vehicleType.equals(priorityType)) {
                    printVehicleMovement(vehicle, road.getNaam());
                    vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam())); // Save for reverse playback
                } else {
                    tempQueue.enqueue(vehicle);  // Hold vehicles not yet processed
                }
            }
            priorityQueue = tempQueue;  // Update queue for the next priority type
        }
    }

    private void processRegularVehicles(Wegdek road, int maxVehiclesPerCycle) {
        int vehiclesToProcess = Math.min(maxVehiclesPerCycle, road.getVehicleCount());
        System.out.println(road.getNaam() + ": Green light for " + vehiclesToProcess + " vehicles.");

        MyQueue<Vehicle> tempQueue = new MyQueue<>();
        for (int i = 0; i < vehiclesToProcess && !road.isWegdekEmpty(); i++) {
            Vehicle vehicle = road.removeVehicleFromWegdek();
            printVehicleMovement(vehicle, road.getNaam(), i + 1);  // FIFO order for regular vehicles
            vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam()));  // Save for reverse playback
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
