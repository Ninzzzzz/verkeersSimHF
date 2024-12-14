package com.ninzzzzz.verkeersSimHF.models;

import com.ninzzzzz.verkeersSimHF.implementations.MyQueue;
import com.ninzzzzz.verkeersSimHF.services.TrafficLightSensor;

public class Wegdek {
    private String naam;
    private MyQueue<Vehicle> vehiclesOnWegdek;
    private TrafficLightSensor sensor; //sensor field

    public Wegdek(String naam, int capacity, TrafficLightSensor sensor) {
        this.naam = naam;
        this.vehiclesOnWegdek = new MyQueue<>();
        this.sensor = sensor;
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
    public TrafficLightSensor getSensor() {
        return sensor;
    }

    public void setSensor(TrafficLightSensor sensor) {
        this.sensor = sensor;
    }

    public void setVehicleQueue(MyQueue<Vehicle> queue) {
        this.vehiclesOnWegdek = queue;
    }

    public MyQueue<Vehicle> getVehicleQueue() {
        return vehiclesOnWegdek;
    }

    public void showVehiclesOnWegdek() {
        MyQueue<Vehicle> tempQueue = new MyQueue<>();
        while (!vehiclesOnWegdek.isEmpty()) {
            Vehicle vehicle = vehiclesOnWegdek.dequeue();
            System.out.println(vehicle);
            tempQueue.enqueue(vehicle);
        }
        vehiclesOnWegdek = tempQueue;
    }
    @Override
    public String toString() {
        return "Wegdek{" +
                "naam='" + naam + '\'' +
                ", vehiclesOnWegdek=" + vehiclesOnWegdek +
                '}';
    }
}
