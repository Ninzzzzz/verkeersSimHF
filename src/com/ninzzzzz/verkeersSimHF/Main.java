package com.ninzzzzz.verkeersSimHF;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;
import com.ninzzzzz.verkeersSimHF.services.VerkeerssimulatieService;

public class Main {
    public static void main(String[] args) {
        Wegdek north = new Wegdek("North", 10); // Set a capacity for the priority queue
        north.addVehicleToWegdek(new Vehicle("AB-1234", 0)); // Regular car
        north.addVehicleToWegdek(new Vehicle("CD-5678", 0)); // Regular car
        north.addVehicleToWegdek(new Vehicle("EF-9012", 3)); // Ambulance (3rd vehicle)
        north.addVehicleToWegdek(new Vehicle("GH-3456", 0)); // Regular car

        Wegdek south = new Wegdek("South", 20); // Set a capacity for the priority queue
        south.addVehicleToWegdek(new Vehicle("IJ-7890", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("KL-2345", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("MN-6789", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("OP-0123", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("QR-4567", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("ST-8901", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("UV-2345", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("WX-6789", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("YZ-0123", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("AB-4567", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("CD-8901", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("EF-2345", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("GH-6789", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("IJ-0123", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("KL-4567", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("MN-8901", 0)); // Regular car
        south.addVehicleToWegdek(new Vehicle("OP-2345", 1)); // Fire truck (17th vehicle)
        south.addVehicleToWegdek(new Vehicle("QR-6789", 0)); // Regular car

        Wegdek east = new Wegdek("East", 10); // Set a capacity for the priority queue
        east.addVehicleToWegdek(new Vehicle("ST-0123", 0)); // Regular car
        east.addVehicleToWegdek(new Vehicle("UV-4567", 0)); // Regular car
        east.addVehicleToWegdek(new Vehicle("WX-8901", 0)); // Regular car
        east.addVehicleToWegdek(new Vehicle("YZ-2345", 0)); // Regular car
        east.addVehicleToWegdek(new Vehicle("AB-6789", 0)); // Regular car

        Wegdek west = new Wegdek("West", 15); // Set a capacity for the priority queue
        west.addVehicleToWegdek(new Vehicle("CD-0123", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("EF-4567", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("GH-8901", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("IJ-2345", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("KL-6789", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("MN-0123", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("OP-4567", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("QR-8901", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("ST-2345", 1)); // Police vehicle (9th vehicle)
        west.addVehicleToWegdek(new Vehicle("UV-6789", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("WX-0123", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("YZ-4567", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("AB-8901", 0)); // Regular car
        west.addVehicleToWegdek(new Vehicle("CD-2345", 0)); // Regular car

        Wegdek[] roads = {north, south, east, west};

        VerkeerssimulatieService service = new VerkeerssimulatieService();
        service.calculateTrafficLightCycles(roads);

        // Reverse playback for all roads
        for (Wegdek road : roads) {
            System.out.println("Reversed Playback for " + road.getNaam() + ":");

            service.normalPlayback(roads); // Normal playback for all roads

            service.reversePlayback();     // Reverse playback for all roads
        }  }
    }
