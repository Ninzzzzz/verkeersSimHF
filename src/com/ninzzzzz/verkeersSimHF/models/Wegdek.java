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

    public void addVehicleToWegdek(Vehicle vehicle) {
        vehiclesOnWegdek.enqueue(vehicle);
    }

    public Vehicle removeVehicleFromWegdek() {
        return vehiclesOnWegdek.dequeue();
    }

    public Vehicle peekNextVehicle() {
        return vehiclesOnWegdek.peek();
    }

    public boolean isWegdekEmpty() {
        return vehiclesOnWegdek.isEmpty();
    }

    public MyPriorityQueue<Vehicle> getVehicleQueue() {
        return vehiclesOnWegdek;
    }

    public int getVehicleCount() {
        return vehiclesOnWegdek.size();
    }

    public void showVehiclesOnWegdek() {
        while (!vehiclesOnWegdek.isEmpty()) {
            System.out.println(vehiclesOnWegdek.dequeue());
        }
    }
}
