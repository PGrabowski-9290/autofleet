package com.paweu.autofleet.data.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@Table("public.invoicePosition")
public class InvoicePos {
    @Id @Column("id")
    private UUID id;
    @Column("invoice_id")
    private UUID invoiceId;
    @Column("name")
    private String name;
    @Column("price")
    private BigDecimal price;
    @Column("quantity")
    private BigDecimal quantity;

    public static InvoicePos fromRow(Map<String, Object> row){
        if (row.get("invpos_id") != null){
            return InvoicePos.builder()
                    .id((UUID) row.get("invpos_id"))
                    .invoiceId((UUID) row.get("invpos_invoice_id"))
                    .name((String) row.get("invpos_name"))
                    .price((BigDecimal) row.get("invpos_price"))
                    .quantity((BigDecimal) row.get("invpos_quantity"))
                    .build();
        } else {
            return null;
        }
    }
}
