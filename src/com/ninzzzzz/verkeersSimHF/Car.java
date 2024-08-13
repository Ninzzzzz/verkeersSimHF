package com.ninzzzzz.verkeersSimHF;
public class Car implements Comparable<Car> {
    private String licensePlate;
    private int priority;  // 1 for high priority, 0 for normal

    public Car(String licensePlate, int priority) {
        this.licensePlate = licensePlate;
        this.priority = priority;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Car other) {
        // Higher priority vehicles should come first
        return Integer.compare(other.priority, this.priority);
    }
}
