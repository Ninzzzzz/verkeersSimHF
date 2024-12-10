package com.ninzzzzz.verkeersSimHF;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;
import com.ninzzzzz.verkeersSimHF.services.VerkeerssimulatieService;

public class Main {
    public static void main(String[] args) {
        int followNumber = 1; // Start the follow number at 1 and increment it globally

        // Initialize roads (Wegdek) with vehicles and assign follow numbers
        Wegdek north = new Wegdek("North");
        north.addVehicleToWegdek(new Vehicle("AB-1234", 0, followNumber++));  // Regular car
        north.addVehicleToWegdek(new Vehicle("CD-5678", 0, followNumber++));  // Regular car
        north.addVehicleToWegdek(new Vehicle("EF-9012", 3, followNumber++));  // Police
        north.addVehicleToWegdek(new Vehicle("GH-3456", 0, followNumber++));  // Regular car

        Wegdek south = new Wegdek("South");
        south.addVehicleToWegdek(new Vehicle("IJ-7890", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("KL-2345", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("MN-6789", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("OP-0123", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("QR-4567", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("ST-8901", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("UV-2345", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("WX-6789", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("YZ-0123", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("AB-4567", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("CD-8901", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("EF-2345", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("GH-6789", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("IJ-0123", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("KL-4567", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("MN-8901", 0, followNumber++));  // Regular car
        south.addVehicleToWegdek(new Vehicle("OP-2345", 2, followNumber++));  // Fire Truck
        south.addVehicleToWegdek(new Vehicle("QR-6789", 0, followNumber++));  // Regular car

        Wegdek east = new Wegdek("East");
        east.addVehicleToWegdek(new Vehicle("ST-0123", 0, followNumber++));  // Regular car
        east.addVehicleToWegdek(new Vehicle("UV-4567", 0, followNumber++));  // Regular car
        east.addVehicleToWegdek(new Vehicle("WX-8901", 0, followNumber++));  // Regular car
        east.addVehicleToWegdek(new Vehicle("YZ-2345", 0, followNumber++));  // Regular car
        east.addVehicleToWegdek(new Vehicle("AB-6789", 0, followNumber++));  // Regular car

        Wegdek west = new Wegdek("West");
        west.addVehicleToWegdek(new Vehicle("CD-0123", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("EF-4567", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("GH-8901", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("IJ-2345", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("KL-6789", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("MN-0123", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("OP-4567", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("QR-8901", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("ST-2345", 1, followNumber++));  // Ambulance
        west.addVehicleToWegdek(new Vehicle("UV-6789", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("WX-0123", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("YZ-4567", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("AB-8901", 0, followNumber++));  // Regular car
        west.addVehicleToWegdek(new Vehicle("CD-2345", 0, followNumber++));  // Regular car

        // Array of roads to pass to the simulation service
        Wegdek[] roads = {north, south, east, west};

        // Initialize and run the simulation service
        VerkeerssimulatieService service = new VerkeerssimulatieService();

        // Normal playback to simulate the vehicles driving away
        service.calculateTrafficLightCycles(roads);

        // Reverse playback to restore the vehicles to their starting positions
        service.reversePlayback();
    }
}
