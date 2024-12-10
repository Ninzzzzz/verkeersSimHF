package com.ninzzzzz.verkeersSimHF.models;

import com.ninzzzzz.verkeersSimHF.implementations.MyQueue;

public class Wegdek {
    private final String naam;
    private MyQueue<Vehicle> vehiclesOnWegdek;

    public Wegdek(String naam) {
        this.naam = naam;
        this.vehiclesOnWegdek = new MyQueue<>();
    }

    public String getNaam() {
        return naam;
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

    public int getVehicleCount() {
        int count = 0;
        MyQueue<Vehicle> tempQueue = new MyQueue<>();
        while (!vehiclesOnWegdek.isEmpty()) {
            tempQueue.enqueue(vehiclesOnWegdek.dequeue());
            count++;
        }
        vehiclesOnWegdek = tempQueue;
        return count;
    }

    public void setVehicleQueue(MyQueue<Vehicle> queue) {
        this.vehiclesOnWegdek = queue;
    }

    public MyQueue<Vehicle> getVehicleQueue() {
        return vehiclesOnWegdek;
    }
}
