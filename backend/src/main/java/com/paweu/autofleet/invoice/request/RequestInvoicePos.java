package com.paweu.autofleet.invoice.request;

import java.math.BigDecimal;

public record RequestInvoicePos(
        String name,
        BigDecimal price,
        BigDecimal qty
) {
}
