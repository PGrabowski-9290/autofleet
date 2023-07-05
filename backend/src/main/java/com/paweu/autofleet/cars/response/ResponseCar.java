package com.paweu.autofleet.cars.response;

import java.sql.Timestamp;

public record ResponseCar(String id,
                          String userId,
                          String brand,
                          String model,
                          int year,
                          String carType,
                          String engineType,
                          String engineSize,
                          int odometer,
                          String numberPlate,
                          Timestamp lastUpdate) {
}
