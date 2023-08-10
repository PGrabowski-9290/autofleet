package com.paweu.autofleet.invoice.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RequestInvoice(
        UUID eventId,
        @NotNull(message = "Date field is required")
        LocalDate date,
        @NotNull(message = "Invoice number field is required")
        String invoiceNumber,
        @NotNull(message = "Currency field is required")
        String currency,
        @Size(min = 1, message = "At least one position should be provided")
        List<RequestInvoicePos> invoicePosList
) {
}
