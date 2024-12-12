package com.ninzzzzz.verkeersSimHF;

import com.ninzzzzz.verkeersSimHF.models.Vehicle;
import com.ninzzzzz.verkeersSimHF.models.Wegdek;
import com.ninzzzzz.verkeersSimHF.services.VerkeerssimulatieService;

public class Main {
    public static void main(String[] args) {
        VerkeerssimulatieService service = new VerkeerssimulatieService();

        // Initialize roads (Wegdek) with vehicles and assign follow numbers
        Wegdek north = new Wegdek("North", 10);
        service.addToNorth(north);

        Wegdek south = new Wegdek("South", 20);
        service.addToSouth(south);

        Wegdek east = new Wegdek("East", 10);
        service.addToEast(east);

        Wegdek west = new Wegdek("West", 15);
        service.addToWest(west);
        // Array of roads to pass to the simulation service
        Wegdek[] roads = {north, south, east, west};

        // Initialize and run the simulation service

        // Normal playback to simulate the vehicles driving away
        service.calculateTrafficLightCycles(roads);

        // Reverse playback to restore the vehicles to their starting positions
        service.reversePlayback();
    }
}
