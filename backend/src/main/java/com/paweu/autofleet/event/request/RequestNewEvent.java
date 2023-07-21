package com.paweu.autofleet.event.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public record RequestNewEvent(
        @NotNull(message = "CarID is required")
        UUID carId,
        @NotNull(message = "Event date value is required")
        LocalDate eventDate,
        @NotNull(message = "Odometer value is required")
        Integer odometer,
        boolean oil,
        boolean oilFilter,
        boolean airFilter,
        boolean timingBeltKit,
        String description) {
}
