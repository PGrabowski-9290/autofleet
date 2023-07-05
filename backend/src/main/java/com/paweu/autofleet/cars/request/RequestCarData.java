package com.paweu.autofleet.cars.request;

public record RequestCarData(String brand,
                             String model,
                             int year,
                             String carType,
                             String engineType,
                             String engineSize,
                             int odometer,
                             String numberPlate) {
}
