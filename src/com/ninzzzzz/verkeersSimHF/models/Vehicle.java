package com.ninzzzzz.verkeersSimHF.models;

public class Vehicle {
    private String id;        // kentekenplaat volgens surinaamse standaarden
    private int priority;     // Priority level, hoger nummer means higher priority
    private int followNumber; // The follow number assigned to this vehicle (per wegdek)

    public Vehicle(String id, int priority, int followNumber) {
        this.id = id;
        this.priority = priority;
        this.followNumber = followNumber;
    }

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public int getFollowNumber() {
        return followNumber;
    }

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
