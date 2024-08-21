package com.ninzzzzz.verkeersSimHF.services;
import java.util.*;

import com.ninzzzzz.verkeersSimHF.implementations.MyQueue;
import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;
public class VerkeerssimulatieService {

    private List<Wegdek> playbackOrder = new ArrayList<>();

    // Normal playback
    public void normalPlayback(Wegdek[] roads) {
        playbackOrder.clear(); // Clear previous playback order
        for (Wegdek road : roads) {
            playbackOrder.add(road); // Record the road in playback order
            MyQueue<Vehicle> queue = road.getVehicleQueue(); // Get the queue of vehicles
            System.out.println("Normal Playback for " + road.getNaam() + ":");
            int vehicleNumber = 1; // Initialize vehicle number for the current road
            while (!queue.isEmpty()) {
                Vehicle vehicle = queue.dequeue();
                String vehicleType = getVehicleType(vehicle.getPriority());
                if (vehicle.getPriority() == 1) {
                    // High-priority vehicle (Police, Firetruck, Ambulance)
                    System.out.println("Vehicle " + vehicle.getLicensePlate() + " (" + vehicleType + ") drives away.");
                } else {
                    // Normal vehicle
                    System.out.println("Vehicle " + vehicle.getLicensePlate() + " (" + vehicleNumber + ") drives away.");
                    vehicleNumber++;
                }
            }
            System.out.println(); // Newline for readability between roads
        }
    }

    // Reverse playback
    public void reversePlayback() {
        Collections.reverse(playbackOrder); // Reverse the order of playback
        for (Wegdek road : playbackOrder) {
            MyQueue<Vehicle> queue = road.getVehicleQueue(); // Get the queue of vehicles
            System.out.println("Reverse Playback for " + road.getNaam() + ":");
            Vehicle[] vehicles = new Vehicle[queue.size()];
            int index = 0;
            while (!queue.isEmpty()) {
                vehicles[index++] = queue.dequeue(); // Collect vehicles in an array
            }
            for (int i = vehicles.length - 1; i >= 0; i--) {
                Vehicle vehicle = vehicles[i];
                String vehicleType = getVehicleType(vehicle.getPriority());
                if (vehicle.getPriority() == 1) {
                    // High-priority vehicle (Police, Firetruck, Ambulance)
                    System.out.println("Vehicle " + vehicle.getLicensePlate() + " (" + vehicleType + ") drives away.");
                } else {
                    // Normal vehicle
                    System.out.println("Vehicle " + vehicle.getLicensePlate() + " (" + (vehicles.length - i) + ") drives away.");
                }
            }
            System.out.println(); // Newline for readability between roads
        }
    }

    // Helper method to get the vehicle type based on priority
    private String getVehicleType(int priority) {
        switch (priority) {
            case 1:
                return "Ambulance";
            case 2:
                return "Police";
            case 3:
                return "Firetruck";
            default:
                return "Regular";
        }
    }
}
