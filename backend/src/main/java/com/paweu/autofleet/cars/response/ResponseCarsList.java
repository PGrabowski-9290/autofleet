package com.paweu.autofleet.cars.response;

import com.paweu.autofleet.data.models.Car;

import java.util.Collection;

public record ResponseCarsList(String message,
                               Collection<Car> list) {
}
