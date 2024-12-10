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
    private int globalFollowNumber = 1; // Logical follow number for all vehicles

    public void calculateTrafficLightCycles(Wegdek[] roads) {
        playbackOrder = new MyLinkedList<>();
        boolean hasMoreVehicles;

        processAllPriorityVehicles(roads);

        do {
            hasMoreVehicles = false;
            for (Wegdek road : roads) {
                if (!road.isWegdekEmpty()) {
                    processRegularVehicles(road);
                    playbackOrder.add(road);
                    hasMoreVehicles = true;
                    System.out.println();
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
            road.setVehicleQueue(tempQueue);
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
                    printVehicleMovement(vehicle, road.getNaam(), globalFollowNumber++);
                    vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam(), globalFollowNumber - 1));
                } else {
                    tempQueue.enqueue(vehicle);
                }
            }
            priorityQueue = tempQueue;
        }
    }

    private void processRegularVehicles(Wegdek road) {
        int vehiclesToProcess = Math.min(5, road.getVehicleCount());
        System.out.println(road.getNaam() + ": Green light for " + vehiclesToProcess + " vehicles.");

        for (int i = 0; i < vehiclesToProcess && !road.isWegdekEmpty(); i++) {
            Vehicle vehicle = road.removeVehicleFromWegdek();
            printVehicleMovement(vehicle, road.getNaam(), globalFollowNumber++);
            vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam(), globalFollowNumber - 1));
        }
    }

    private void printVehicleMovement(Vehicle vehicle, String roadName, int followNumber) {
        String vehicleType = getVehicleType(vehicle.getPriority());
        if (vehicleType.equals("Regular")) {
            System.out.println("Vehicle " + vehicle.getId() + " with follow number " + followNumber + " on " + roadName + " drives away.");
        } else {
            System.out.println(vehicleType + " " + vehicle.getId() + " with follow number " + followNumber + " on " + roadName + " drives away.");
        }
    }

    private String getVehicleType(int priority) {
        switch (priority) {
            case 1: return "Ambulance";
            case 2: return "Fire Truck";
            case 3: return "Police";
            default: return "Regular";
        }
    }

    public void reversePlayback() {
        System.out.println("Reverse Playback:");
        while (!vehicleMovementStack.isEmpty()) {
            VehicleMovement movement = vehicleMovementStack.pop();
            Vehicle vehicle = movement.getVehicle();
            String roadName = movement.getRoadName();
            int followNumber = movement.getFollowNumber();
            String vehicleType = getVehicleType(vehicle.getPriority());

            if (vehicleType.equals("Regular")) {
                System.out.println("Vehicle " + vehicle.getId() + " with follow number " + followNumber + " drives back to position " + followNumber + " on " + roadName + ".");
            } else {
                System.out.println(vehicleType + " " + vehicle.getId() + " with follow number " + followNumber + " drives back to position " + followNumber + " on " + roadName + ".");
            }
        }
    }
}
