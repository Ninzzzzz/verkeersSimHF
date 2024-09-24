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
    private int globalFollowNumber = 1;  // Follow number across all roads

    public void calculateTrafficLightCycles(Wegdek[] roads) {
        playbackOrder = new MyLinkedList<>(); // Reset playback order
        int maxVehiclesPerCycle = 5;
        boolean hasMoreVehicles;

        processAllPriorityVehicles(roads);

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
            MyQueue<Vehicle> priorityQueue = new MyQueue<>();

            while (!road.isWegdekEmpty()) {
                Vehicle vehicle = road.peekNextVehicle();
                if (vehicle.getPriority() > 0) {
                    priorityQueue.enqueue(road.removeVehicleFromWegdek());
                } else {
                    tempQueue.enqueue(road.removeVehicleFromWegdek());
                }
            }

            processPriorityVehicles(priorityQueue, road);

            road.setVehicleQueue(tempQueue);  // Restore FIFO order for regular vehicles
        }
    }

    private void processPriorityVehicles(MyQueue<Vehicle> priorityQueue, Wegdek road) {
        String[] priorityOrder = {"Police", "Fire Truck", "Ambulance"};

        for (String priorityType : priorityOrder) {
            MyQueue<Vehicle> tempQueue = new MyQueue<>();
            while (!priorityQueue.isEmpty()) {
                Vehicle vehicle = priorityQueue.dequeue();
                String vehicleType = getVehicleType(vehicle.getPriority());
                if (vehicleType.equals(priorityType)) {
                    printVehicleMovementWithFollowNumber(vehicle, road.getNaam());
                    vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam()));
                } else {
                    tempQueue.enqueue(vehicle);
                }
            }
            priorityQueue = tempQueue;
        }
    }

    private void processRegularVehicles(Wegdek road, int maxVehiclesPerCycle) {
        int vehiclesToProcess = Math.min(maxVehiclesPerCycle, road.getVehicleCount());
        System.out.println(road.getNaam() + ": Green light for " + vehiclesToProcess + " vehicles.");

        MyQueue<Vehicle> tempQueue = new MyQueue<>();
        for (int i = 0; i < vehiclesToProcess && !road.isWegdekEmpty(); i++) {
            Vehicle vehicle = road.removeVehicleFromWegdek();
            printVehicleMovementWithFollowNumber(vehicle, road.getNaam());
            vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam()));
        }
    }

    private void printVehicleMovementWithFollowNumber(Vehicle vehicle, String roadName) {
        String vehicleType = getVehicleType(vehicle.getPriority());
        if (vehicleType.equals("Regular")) {
            System.out.println("Vehicle " + vehicle.getId() + " with follow number " + vehicle.getFollowNumber() + " on " + roadName + " drives away.");
        } else {
            System.out.println(vehicleType + " " + vehicle.getId() + " with follow number " + vehicle.getFollowNumber() + " on " + roadName + " drives away.");
        }
    }

    private String getVehicleType(int priority) {
        switch (priority) {
            case 3:
                return "Police";
            case 2:
                return "Fire Truck";
            case 1:
                return "Ambulance";
            default:
                return "Regular";
        }
    }

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
