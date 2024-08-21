package com.ninzzzzz.verkeersSimHF.models;

public class Vehicle implements Comparable<Vehicle> {
    private String id;        // License plate following Surinamese standards
    private int priority;     // Priority level, where a higher number means higher priority

    public Vehicle(String id, int priority) {
        this.id = id;
        this.priority = priority;
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    // Compare based on priority for priority queue operations
    @Override
    public int compareTo(Vehicle other) {
        return Integer.compare(other.priority, this.priority); // Higher priority vehicles first
    }

    // Override toString() for better readability during debugging
    @Override
    public String toString() {
        return "Vehicle{id='" + id + "', priority=" + priority + "}";
    }

    // Additional methods related to Vehicle's behavior can be added here
}
