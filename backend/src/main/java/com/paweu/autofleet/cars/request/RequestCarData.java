package com.paweu.autofleet.cars.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record RequestCarData(
        @NotNull(message = "Brand is required") @NotEmpty(message = "Brand is required")
        String brand,
        @NotNull(message = "Model is required") @NotEmpty(message = "Model is required")
        String model,
        @NotNull(message = "Year is required")
        Integer year,
        @NotNull(message = "CarType is required") @NotEmpty(message = "CarType is required")
        String carType,
        @NotNull(message = "EngineType is required") @NotEmpty(message = "EngineType is required")
        String engineType,
        @NotNull(message = "Engine is required") @NotEmpty(message = "Engine is required")
        String engine,
        @NotNull(message = "odometer is required")
        Integer odometer,
        @NotNull(message = "NumberPlate is required") @NotEmpty(message = "NumberPlate is required")
        String numberPlate) {
}
