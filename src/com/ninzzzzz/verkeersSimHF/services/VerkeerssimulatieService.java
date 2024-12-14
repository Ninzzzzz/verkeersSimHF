package com.ninzzzzz.verkeersSimHF.services;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;
import com.ninzzzzz.verkeersSimHF.implementations.MyStack;
import com.ninzzzzz.verkeersSimHF.implementations.MyQueue;
import com.ninzzzzz.verkeersSimHF.implementations.MyLinkedList;

public class VerkeerssimulatieService {
    private MyLinkedList<Wegdek> playbackOrder = new MyLinkedList<>(); // Order of roads processed
    private MyStack<VehicleMovement> vehicleMovementStack = new MyStack<>(); // Stack for reverse playback
    private int totalCycles = 0; // Count of meaningful cycles

    public void calculateTrafficLightCycles(Wegdek[] roads) {
        playbackOrder = new MyLinkedList<>(); // Reset playback order
        totalCycles = 0; // Reset cycle count to start fresh
        boolean hasMoreVehicles;

        processAllPriorityVehicles(roads); // Handles high-priority vehicles first

        do {
            hasMoreVehicles = false; // Tracks if there are more vehicles to process

            for (Wegdek road : roads) {
                if (!road.isWegdekEmpty()) {
                    TrafficLightSensor sensor = road.getSensor(); // Get the sensor for this road
                    int vehicleCount = road.getVehicleCount();

                    // Sensor logic: skip green light if conditions are met
                    if (sensor.shouldSkipGreenLight(vehicleCount)) {
                        System.out.println(road.getNaam() + ": Skipping green light (sensor condition).");
                        continue; // Skip to the next road
                    }

                    // Sensor logic: determine how many vehicles to process
                    int vehiclesToProcess;
                    if (sensor.shouldExtendGreenLight(vehicleCount)) {
                        vehiclesToProcess = Math.min(vehicleCount, 10); // Extend green light for up to 10 vehicles
                        System.out.println(road.getNaam() + ": Extending green light for more vehicles.");
                    } else if (sensor.shouldShortenGreenLight(vehicleCount)) {
                        vehiclesToProcess = Math.min(vehicleCount, 3); // Shorten green light for up to 3 vehicles
                        System.out.println(road.getNaam() + ": Shortening green light for fewer vehicles.");
                    } else {
                        vehiclesToProcess = sensor.vehiclesToAllow(vehicleCount); // Default vehicle processing logic
                    }

                    System.out.println(road.getNaam() + ": Green light for " + vehiclesToProcess + " vehicles.");

                    // Process the vehicles on the current road
                    for (int i = 0; i < vehiclesToProcess && !road.isWegdekEmpty(); i++) {
                        Vehicle vehicle = road.removeVehicleFromWegdek();
                        printVehicleMovementWithFollowNumber(vehicle, road.getNaam());
                        vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam())); // Record movement for playback
                    }

                    playbackOrder.add(road); // Record the road for playback
                    hasMoreVehicles = true; // Vehicles were processed, so more cycles are needed
                    System.out.println(); // Adds space for readability in output
                }
            }

            // Increment total cycles if any vehicles were processed in this pass
            if (hasMoreVehicles) {
                totalCycles++;
            }
        } while (hasMoreVehicles);

        System.out.println("Total Cycles: " + totalCycles); // Print the total number of cycles
    }

    public void processAllPriorityVehicles(Wegdek[] roads) {
        for (Wegdek road : roads) {
            MyQueue<Vehicle> tempQueue = new MyQueue<>(); // Temporary queue for regular vehicles
            MyQueue<Vehicle> priorityQueue = new MyQueue<>(); // Queue for priority vehicles

            // Separate priority and regular vehicles
            while (!road.isWegdekEmpty()) {
                Vehicle vehicle = road.peekNextVehicle();
                if (vehicle.getPriority() > 0) { // Priority vehicles
                    priorityQueue.enqueue(road.removeVehicleFromWegdek());
                } else { // Regular vehicles
                    tempQueue.enqueue(road.removeVehicleFromWegdek());
                }
            }

            processPriorityVehicles(priorityQueue, road); // Process priority vehicles

            road.setVehicleQueue(tempQueue); // Restore regular vehicles to the road in original order
        }
    }

    private void processPriorityVehicles(MyQueue<Vehicle> priorityQueue, Wegdek road) {
        String[] priorityOrder = {"Police", "Fire Truck", "Ambulance"}; // Order of priority vehicles

        for (String priorityType : priorityOrder) {
            MyQueue<Vehicle> tempQueue = new MyQueue<>(); // Temporary queue for non-matching vehicles
            while (!priorityQueue.isEmpty()) {
                Vehicle vehicle = priorityQueue.dequeue();
                String vehicleType = getVehicleType(vehicle.getPriority());
                if (vehicleType.equals(priorityType)) {
                    printVehicleMovementWithFollowNumber(vehicle, road.getNaam());
                    vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam())); // Record movement for playback
                } else {
                    tempQueue.enqueue(vehicle); // Non-matching vehicles go to the temp queue
                }
            }
            priorityQueue = tempQueue; // Update the priority queue for the next type
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
                System.out.println("Vehicle " + vehicle.getId() + " drives back to position follow number " + vehicle.getFollowNumber() + " on " + roadName + ".");
            } else {
                System.out.println(vehicleType + " " + vehicle.getId() + " drives back to position follow number " + vehicle.getFollowNumber() + " on " + roadName + ".");
            }
        }
    }
    public void addToWest(Wegdek west) {
        west.addVehicleToWegdek(new Vehicle("CD-0123", 0, 1));  // Regular vehicle
        west.addVehicleToWegdek(new Vehicle("EF-4567", 0, 2));
        west.addVehicleToWegdek(new Vehicle("GH-8901", 0, 3));
        west.addVehicleToWegdek(new Vehicle("IJ-2345", 0, 4));
        west.addVehicleToWegdek(new Vehicle("KL-6789", 0, 5));
        west.addVehicleToWegdek(new Vehicle("MN-0123", 0, 6));
        west.addVehicleToWegdek(new Vehicle("OP-4567", 0,7));
        west.addVehicleToWegdek(new Vehicle("QR-8901", 0, 8));
        west.addVehicleToWegdek(new Vehicle("ST-2345", 1, 9));  // Ambulance (9th and high prio vehicle)
        west.addVehicleToWegdek(new Vehicle("UV-6789", 0, 10));
        west.addVehicleToWegdek(new Vehicle("WX-0123", 0, 11));
        west.addVehicleToWegdek(new Vehicle("YZ-4567", 0, 12));
        west.addVehicleToWegdek(new Vehicle("AB-8901", 0, 13));
        west.addVehicleToWegdek(new Vehicle("CD-2345", 0, 14));
    }

    public void addToEast(Wegdek east) {
        east.addVehicleToWegdek(new Vehicle("ST-0123", 0, 1));
        east.addVehicleToWegdek(new Vehicle("UV-4567", 0, 2));
        east.addVehicleToWegdek(new Vehicle("WX-8901", 0, 3));
        east.addVehicleToWegdek(new Vehicle("YZ-2345", 0, 4));
        east.addVehicleToWegdek(new Vehicle("AB-6789", 0, 5));
    }

    public void addToSouth(Wegdek south) {
        south.addVehicleToWegdek(new Vehicle("IJ-7890", 0, 1));
        south.addVehicleToWegdek(new Vehicle("KL-2345", 0, 2));
        south.addVehicleToWegdek(new Vehicle("MN-6789", 0, 3));
        south.addVehicleToWegdek(new Vehicle("OP-0123", 0, 4));
        south.addVehicleToWegdek(new Vehicle("QR-4567", 0, 5));
        south.addVehicleToWegdek(new Vehicle("ST-8901", 0, 6));
        south.addVehicleToWegdek(new Vehicle("UV-2345", 0, 7));
        south.addVehicleToWegdek(new Vehicle("WX-6789", 0, 8));
        south.addVehicleToWegdek(new Vehicle("YZ-0123", 0, 9));
        south.addVehicleToWegdek(new Vehicle("AB-4567", 0, 10));
        south.addVehicleToWegdek(new Vehicle("CD-8901", 0, 11));
        south.addVehicleToWegdek(new Vehicle("EF-2345", 0, 12));
        south.addVehicleToWegdek(new Vehicle("GH-6789", 0, 13));
        south.addVehicleToWegdek(new Vehicle("IJ-0123", 0, 14));
        south.addVehicleToWegdek(new Vehicle("KL-4567", 0, 15));
        south.addVehicleToWegdek(new Vehicle("MN-8901", 0, 16));
        south.addVehicleToWegdek(new Vehicle("OP-2345", 2,17));  // Fire truck (17th vehicle)
        south.addVehicleToWegdek(new Vehicle("QR-6789", 0, 18));
    }

    public  void addToNorth(Wegdek north) {
        north.addVehicleToWegdek(new Vehicle("AB-1234", 0, 1));
        north.addVehicleToWegdek(new Vehicle("CD-5678", 0, 2));
        north.addVehicleToWegdek(new Vehicle("EF-9012", 3, 3));  // Police (3rd vehicle)
        north.addVehicleToWegdek(new Vehicle("GH-3456", 0, 4));
    }

}

