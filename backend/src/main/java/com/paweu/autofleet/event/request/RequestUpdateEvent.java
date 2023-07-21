package com.paweu.autofleet.event.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RequestUpdateEvent(
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
