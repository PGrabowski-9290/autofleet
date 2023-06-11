package com.paweu.autofleet.cars.response;

public record ResponseCar(String id,
                          String userId,
                          String brand,
                          String model,
                          int year,
                          String category,
                          String engine,
                          int odometer,
                          String numberPlate) {
}
