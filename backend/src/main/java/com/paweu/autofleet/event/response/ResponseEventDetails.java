package com.paweu.autofleet.event.response;

import com.paweu.autofleet.data.models.Invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ResponseEventDetails(UUID eventId,
                                   UUID carId,
                                   LocalDate eventDate,
                                   LocalDateTime lastUpdate,
                                   Integer odometer,
                                   boolean oil,
                                   boolean oilFilter,
                                   boolean airFilter,
                                   boolean timingBeltKit,
                                   String description,
                                   List<Invoice> invoiceList) {
}
