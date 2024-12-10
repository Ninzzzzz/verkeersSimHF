package com.ninzzzzz.verkeersSimHF.models;

public class Vehicle {
    private String id;         // License plate following Surinamese standards
    private int priority;      // Priority level, where a higher number means higher priority
    private int followNumber;  // The unique follow number for this vehicle

    // Constructor
    public Vehicle(String id, int priority, int followNumber) {
        this.id = id;
        this.priority = priority;
        this.followNumber = followNumber;
    }

    // Getters
    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public int getFollowNumber() {
        return followNumber;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setFollowNumber(int followNumber) {
        this.followNumber = followNumber;
    }

    @Override
    public String toString() {
        return "Vehicle{id='" + id + "', priority=" + priority + ", followNumber=" + followNumber + "}";
    }
}
