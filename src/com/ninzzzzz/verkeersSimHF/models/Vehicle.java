package com.ninzzzzz.verkeersSimHF.models;

public class Vehicle implements Comparable<Vehicle> {
    private String id;        // License plate following Surinamese standards
    private int priority;     // Priority level, where a higher number means higher priority

    public Vehicle(String id, int priority) {
        this.id = id;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(Vehicle other) {
        return Integer.compare(other.priority, this.priority); // Higher priority vehicles first
    }

    @Override
    public String toString() {
        return "Vehicle{id='" + id + "', priority=" + priority + "}";
    }
}
