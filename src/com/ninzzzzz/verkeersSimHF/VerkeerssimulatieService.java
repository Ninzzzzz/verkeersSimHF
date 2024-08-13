package com.ninzzzzz.verkeersSimHF;

import com.ninzzzzz.verkeersSimHF.Car;
import com.ninzzzzz.verkeersSimHF.Wegdek;
import java.util.PriorityQueue;

public class VerkeerssimulatieService {

    public void simulateTraffic(Wegdek road, boolean isRedLight) {
        PriorityQueue<Car> priorityQueue = new PriorityQueue<>(road.getCars());

        while (!priorityQueue.isEmpty()) {
            Car car = priorityQueue.poll();
            if (car.getPriority() == 1) {
                System.out.println("Priority vehicle " + car.getLicensePlate() + " is proceeding on " + road.getName());
            } else {
                if (isRedLight) {
                    System.out.println("Vehicle " + car.getLicensePlate() + " is stopping at the red light on " + road.getName());
                } else {
                    System.out.println("Vehicle " + car.getLicensePlate() + " is proceeding on " + road.getName());
                }
            }
        }
    }
}
