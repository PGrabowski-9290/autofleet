package com.paweu.autofleet.data.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table("public.invoicePosition")
public class InvoicePos {
    @Id @Column("id")
    private UUID id;
    @Column("invoice_id")
    private UUID invoiceId;
    @Column("name")
    private String name;
    @Column("price")
    private long price;
    @Column("quantity")
    private int quantity;
}
