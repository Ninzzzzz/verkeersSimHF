package com.ninzzzzz.verkeersSimHF;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;
import com.ninzzzzz.verkeersSimHF.services.VerkeerssimulatieService;

public class Main {
    public static void main(String[] args) {
        // Initialize roads (Wegdek) with vehicles directly added
        Wegdek north = new Wegdek("North", 10);
        north.addVehicleToWegdek(new Vehicle("AB-1234", 0));  // Regular car
        north.addVehicleToWegdek(new Vehicle("CD-5678", 0));  // Regular car
        north.addVehicleToWegdek(new Vehicle("EF-9012", 3));  // Ambulance (3rd vehicle)
        north.addVehicleToWegdek(new Vehicle("GH-3456", 0));  // Regular car

        Wegdek south = new Wegdek("South", 20);
        south.addVehicleToWegdek(new Vehicle("IJ-7890", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("KL-2345", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("MN-6789", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("OP-0123", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("QR-4567", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("ST-8901", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("UV-2345", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("WX-6789", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("YZ-0123", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("AB-4567", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("CD-8901", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("EF-2345", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("GH-6789", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("IJ-0123", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("KL-4567", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("MN-8901", 0));  // Regular car
        south.addVehicleToWegdek(new Vehicle("OP-2345", 2));  // Fire truck (17th vehicle)
        south.addVehicleToWegdek(new Vehicle("QR-6789", 0));  // Regular car

        Wegdek east = new Wegdek("East", 10);
        east.addVehicleToWegdek(new Vehicle("ST-0123", 0));  // Regular car
        east.addVehicleToWegdek(new Vehicle("UV-4567", 0));  // Regular car
        east.addVehicleToWegdek(new Vehicle("WX-8901", 0));  // Regular car
        east.addVehicleToWegdek(new Vehicle("YZ-2345", 0));  // Regular car
        east.addVehicleToWegdek(new Vehicle("AB-6789", 0));  // Regular car

        Wegdek west = new Wegdek("West", 15);
        west.addVehicleToWegdek(new Vehicle("CD-0123", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("EF-4567", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("GH-8901", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("IJ-2345", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("KL-6789", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("MN-0123", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("OP-4567", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("QR-8901", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("ST-2345", 1));  // Police vehicle (9th vehicle)
        west.addVehicleToWegdek(new Vehicle("UV-6789", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("WX-0123", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("YZ-4567", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("AB-8901", 0));  // Regular car
        west.addVehicleToWegdek(new Vehicle("CD-2345", 0));  // Regular car

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
