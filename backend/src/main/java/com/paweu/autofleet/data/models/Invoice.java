package com.paweu.autofleet.data.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDate;

import java.util.UUID;

@Data
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
    private Timestamp lastUpdate;
}
