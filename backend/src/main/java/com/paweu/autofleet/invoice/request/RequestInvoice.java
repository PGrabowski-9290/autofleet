package com.paweu.autofleet.invoice.request;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record RequestInvoice(
        UUID eventId,
        LocalDate date,
        String invoiceNumber,
        String currency,
        List<RequestInvoicePos> invoicePosList
) {
}
