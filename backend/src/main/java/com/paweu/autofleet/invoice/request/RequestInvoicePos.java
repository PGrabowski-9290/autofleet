package com.paweu.autofleet.invoice.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RequestInvoicePos(
        @NotNull(message = "Name field is required")
        String name,
        @NotNull(message = "Price field is required") @Positive(message = "Cannot be 0 and less than 0")
        BigDecimal price,
        @NotNull(message = "Qty field is required") @Positive(message = "Cannot be 0 and less than 0")
        BigDecimal qty
) {
}
