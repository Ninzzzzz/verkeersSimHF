package com.ninzzzzz.verkeersSimHF;

import com.ninzzzzz.verkeersSimHF.Car;
import com.ninzzzzz.verkeersSimHF.Wegdek;
import com.ninzzzzz.verkeersSimHF.VerkeerssimulatieService;

public class Application {
    public static void main(String[] args) {
        VerkeerssimulatieService service = new VerkeerssimulatieService();

        // Create cars with different priorities
        Car car1 = new Car("AB-1234", 1); // High-priority
        Car car2 = new Car("XYZ-567", 0); // Normal priority
        Car car3 = new Car("CD-7890", 0); // Normal priority
        Car car4 = new Car("EFG-890", 1); // High-priority

        Wegdek road = new Wegdek("Main Street");
        road.addCar(car1);
        road.addCar(car2);
        road.addCar(car3);
        road.addCar(car4);



        boolean isRedLight = true; // Simulate a red light

        service.simulateTraffic(road, isRedLight);  // This will simulate traffic with the red light
    }
}
