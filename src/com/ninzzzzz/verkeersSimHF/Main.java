package com.ninzzzzz.verkeersSimHF;

import com.ninzzzzz.verkeersSimHF.models.Wegdek;
import com.ninzzzzz.verkeersSimHF.services.TrafficLightSensor;
import com.ninzzzzz.verkeersSimHF.services.VerkeerssimulatieService;

public class Main {
    public static void main(String[] args) {
        VerkeerssimulatieService service = new VerkeerssimulatieService();

        // Initialize roads (Wegdek) with vehicles and sensors
        Wegdek north = new Wegdek("North", 10, new TrafficLightSensor(4));
        service.addToNorth(north);

        Wegdek south = new Wegdek("South", 20, new TrafficLightSensor(2) );
        service.addToSouth(south);

        Wegdek east = new Wegdek("East", 10, new TrafficLightSensor(1));
        service.addToEast(east);

        Wegdek west = new Wegdek("West", 15, new TrafficLightSensor(3));
        service.addToWest(west);

        // Array of roads to pass to the simulation service
        Wegdek[] roads = {north, south, east, west};

        service.processAllPriorityVehicles(roads);

        // Normal playback to simulate the vehicles driving away
        service.calculateTrafficLightCycles(roads);

        //reversed version of the normal pb
        service.reversePlayback();
    }
}
