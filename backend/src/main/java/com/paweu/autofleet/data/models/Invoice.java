package com.paweu.autofleet.data.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@Table("public.invoice")
public class Invoice {

    @Id @Column("id")
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("event_id")
    private UUID eventId;
    @Column("invoice_date")
    private LocalDate date;
    @Column("invoice_number")
    private String invoiceNumber;
    @Column("currency")
    private String currency;
    @Column("last_update")
    private LocalDateTime lastUpdate;

    @Transient
    @Builder.Default
    private List<InvoicePos> invoicePosList = new ArrayList<>();

    @Transient
    private BigDecimal total;

    public static Invoice fromRows(List<Map<String, Object>> rows){
        if(rows.get(0).get("inv_id") != null){
            return Invoice.builder()
                    .id((UUID) rows.get(0).get("inv_id"))
                    .userId((UUID) rows.get(0).get("inv_user_id"))
                    .eventId((UUID) rows.get(0).get("e_event_id"))
                    .date((LocalDate) rows.get(0).get("inv_invoice_date"))
                    .invoiceNumber((String) rows.get(0).get("inv_invoice_number"))
                    .currency((String) rows.get(0).get("inv_currency"))
                    .lastUpdate((LocalDateTime) rows.get(0).get("inv_last_update"))
                    .total(rows.stream()
                            .map(row -> ((BigDecimal) row.get("invpos_price")).multiply((BigDecimal) row.get("invpos_quantity")))
                            .reduce(BigDecimal.ZERO, BigDecimal::add)
                            .setScale(2, RoundingMode.HALF_UP))
                    .invoicePosList(rows.stream()
                            .map(InvoicePos::fromRow)
                            .filter(Objects::nonNull)
                            .toList())
                    .build();
        } else {
            return null;
        }
    }
}
