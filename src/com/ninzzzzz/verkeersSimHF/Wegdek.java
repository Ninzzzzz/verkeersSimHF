package com.ninzzzzz.verkeersSimHF;

import java.util.ArrayList;
import java.util.List;

public class Wegdek {
    private String name;
    private List<Car> cars;

    public Wegdek(String name) {
        this.name = name;
        this.cars = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }
}
