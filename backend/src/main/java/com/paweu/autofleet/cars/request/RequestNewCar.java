package com.paweu.autofleet.cars.request;

public record RequestNewCar(String brand,
                            String model,
                            String category,
                            int year,
                            String engine,
                            int odometer,
                            String numberPlate) {
}
