package com.paweu.autofleet.cars.response;

import com.paweu.autofleet.data.models.Event;

import java.time.LocalDateTime;
import java.util.List;

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
                          LocalDateTime lastUpdate,
                          List<Event> events) {
}
