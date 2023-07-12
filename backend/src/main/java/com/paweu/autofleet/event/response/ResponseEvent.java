package com.paweu.autofleet.event.response;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record ResponseEvent(UUID eventId,
                            UUID carId,
                            LocalDate eventDate,
                            LocalDateTime lastUpdate,
                            Integer odometer,
                            boolean oil,
                            boolean oilFilter,
                            boolean airFilter,
                            boolean timingBeltKit,
                            String description) {
}
