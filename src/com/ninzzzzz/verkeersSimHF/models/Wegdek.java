package com.ninzzzzz.verkeersSimHF.models;

import com.ninzzzzz.verkeersSimHF.implementations.MyPriorityQueue;

public class Wegdek {
    private String naam;
    private MyPriorityQueue<Vehicle> vehiclesOnWegdek;

    public Wegdek(String naam, int capacity) {
        this.naam = naam;
        this.vehiclesOnWegdek = new MyPriorityQueue<>(capacity);
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    // Enqueue vehicles with priority
    public void addVehicleToWegdek(Vehicle vehicle) {
        vehiclesOnWegdek.enqueue(vehicle, vehicle.getPriority());
    }

    // Dequeue the highest-priority vehicle
    public Vehicle removeVehicleFromWegdek() {
        return vehiclesOnWegdek.dequeue();
    }

    // Peek at the next vehicle without dequeuing
    public Vehicle peekNextVehicle() {
        return vehiclesOnWegdek.peek();
    }

    public boolean isWegdekEmpty() {
        return vehiclesOnWegdek.isEmpty();
    }

    public int getVehicleCount() {
        return vehiclesOnWegdek.size();
    }
}
