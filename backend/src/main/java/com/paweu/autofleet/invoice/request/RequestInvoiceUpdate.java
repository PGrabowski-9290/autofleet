package com.paweu.autofleet.invoice.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RequestInvoiceUpdate(
        UUID eventId,
        LocalDate date,
        String invoiceNumber,
        String currency,
        List<RequestInvoicePos> invoicePosList
) {
}
