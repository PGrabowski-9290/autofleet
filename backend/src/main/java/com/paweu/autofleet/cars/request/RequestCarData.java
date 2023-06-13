package com.paweu.autofleet.cars.request;

public record RequestCarData(String brand,
                             String model,
                             String category,
                             int year,
                             String engine,
                             int odometer,
                             String numberPlate) {
}
