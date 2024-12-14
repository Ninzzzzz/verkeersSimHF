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
        totalCycles = 0; // Reset cycle count (begint weer bij 0)
        boolean hasMoreVehicles;

        processAllPriorityVehicles(roads); // Handelt eerst high prio vehicles

        do {
            hasMoreVehicles = false;

            for (Wegdek road : roads) {
                if (!road.isWegdekEmpty()) {
                    TrafficLightSensor sensor = road.getSensor(); // Get the sensor
                    int vehicleCount = road.getVehicleCount();

                    // sensor Skipt green light als wegdek voldoet aan de condities
                    if (sensor.shouldSkipGreenLight(vehicleCount)) {
                        System.out.println(road.getNaam() + ": Skipping green light (sensor condition).");
                        continue;
                    }

                    // Gaat eerst actie uitvoeren als aantal vehicles op wegdek voldoen aan condities
                    int vehiclesToProcess;
                    if (sensor.shouldExtendGreenLight(vehicleCount)) {
                        vehiclesToProcess = Math.min(vehicleCount, 10);
                        System.out.println(road.getNaam() + ": Extending green light for more vehicles.");
                    } else if (sensor.shouldShortenGreenLight(vehicleCount)) {
                        vehiclesToProcess = Math.min(vehicleCount, 3);
                        System.out.println(road.getNaam() + ": Shortening green light for fewer vehicles.");
                    } else {
                        vehiclesToProcess = sensor.vehiclesToAllow(vehicleCount);
                    }

                    System.out.println(road.getNaam() + ": Green light for " + vehiclesToProcess + " vehicles.");

                    // processes the vehicles
                    for (int i = 0; i < vehiclesToProcess && !road.isWegdekEmpty(); i++) {
                        Vehicle vehicle = road.removeVehicleFromWegdek();
                        printVehicleMovementWithFollowNumber(vehicle, road.getNaam());
                        vehicleMovementStack.push(new VehicleMovement(vehicle, road.getNaam()));
                    }

                    playbackOrder.add(road); // Records the order of the wegdeks processed
                    hasMoreVehicles = true;
                    System.out.println(); // gewoon voor betere readability
                }
            }

            totalCycles++;
        } while (hasMoreVehicles);

        System.out.println("Total Cycles: " + totalCycles);
    }

    public void processAllPriorityVehicles(Wegdek[] roads) {
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

            road.setVehicleQueue(tempQueue); // Restore FIFO order for regular vehicles
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
        west.addVehicleToWegdek(new Vehicle("CD-0123", 0, 1));  // Regular car
        west.addVehicleToWegdek(new Vehicle("EF-4567", 0, 2));  // Regular car
        west.addVehicleToWegdek(new Vehicle("GH-8901", 0, 3));  // Regular car
        west.addVehicleToWegdek(new Vehicle("IJ-2345", 0, 4));  // Regular car
        west.addVehicleToWegdek(new Vehicle("KL-6789", 0, 5));  // Regular car
        west.addVehicleToWegdek(new Vehicle("MN-0123", 0, 6));  // Regular car
        west.addVehicleToWegdek(new Vehicle("OP-4567", 0,7));  // Regular car
        west.addVehicleToWegdek(new Vehicle("QR-8901", 0, 8));  // Regular car
        west.addVehicleToWegdek(new Vehicle("ST-2345", 1, 9));  // Ambulance (9th vehicle)
        west.addVehicleToWegdek(new Vehicle("UV-6789", 0, 10));  // Regular car
        west.addVehicleToWegdek(new Vehicle("WX-0123", 0, 11));  // Regular car
        west.addVehicleToWegdek(new Vehicle("YZ-4567", 0, 12));  // Regular car
        west.addVehicleToWegdek(new Vehicle("AB-8901", 0, 13));  // Regular car
        west.addVehicleToWegdek(new Vehicle("CD-2345", 0, 14));  // Regular car
    }

    public void addToEast(Wegdek east) {
        east.addVehicleToWegdek(new Vehicle("ST-0123", 0, 1));  // Regular car
        east.addVehicleToWegdek(new Vehicle("UV-4567", 0, 2));  // Regular car
        east.addVehicleToWegdek(new Vehicle("WX-8901", 0, 3));  // Regular car
        east.addVehicleToWegdek(new Vehicle("YZ-2345", 0, 4));  // Regular car
        east.addVehicleToWegdek(new Vehicle("AB-6789", 0, 5));  // Regular car
    }

    public void addToSouth(Wegdek south) {
        south.addVehicleToWegdek(new Vehicle("IJ-7890", 0, 1));  // Regular car
        south.addVehicleToWegdek(new Vehicle("KL-2345", 0, 2));  // Regular car
        south.addVehicleToWegdek(new Vehicle("MN-6789", 0, 3));  // Regular car
        south.addVehicleToWegdek(new Vehicle("OP-0123", 0, 4));  // Regular car
        south.addVehicleToWegdek(new Vehicle("QR-4567", 0, 5));  // Regular car
        south.addVehicleToWegdek(new Vehicle("ST-8901", 0, 6));  // Regular car
        south.addVehicleToWegdek(new Vehicle("UV-2345", 0, 7));  // Regular car
        south.addVehicleToWegdek(new Vehicle("WX-6789", 0, 8));  // Regular car
        south.addVehicleToWegdek(new Vehicle("YZ-0123", 0, 9));  // Regular car
        south.addVehicleToWegdek(new Vehicle("AB-4567", 0, 10));  // Regular car
        south.addVehicleToWegdek(new Vehicle("CD-8901", 0, 11));  // Regular car
        south.addVehicleToWegdek(new Vehicle("EF-2345", 0, 12));  // Regular car
        south.addVehicleToWegdek(new Vehicle("GH-6789", 0, 13));  // Regular car
        south.addVehicleToWegdek(new Vehicle("IJ-0123", 0, 14));  // Regular car
        south.addVehicleToWegdek(new Vehicle("KL-4567", 0, 15));  // Regular car
        south.addVehicleToWegdek(new Vehicle("MN-8901", 0, 16));  // Regular car
        south.addVehicleToWegdek(new Vehicle("OP-2345", 2,17));  // Fire truck (17th vehicle)
        south.addVehicleToWegdek(new Vehicle("QR-6789", 0, 18));  // Regular car
    }

    public  void addToNorth(Wegdek north) {
        north.addVehicleToWegdek(new Vehicle("AB-1234", 0, 1));  // Regular car
        north.addVehicleToWegdek(new Vehicle("CD-5678", 0, 2));  // Regular car
        north.addVehicleToWegdek(new Vehicle("EF-9012", 3, 3));  // Police (3rd vehicle)
        north.addVehicleToWegdek(new Vehicle("GH-3456", 0, 4));  // Regular car
    }

}

