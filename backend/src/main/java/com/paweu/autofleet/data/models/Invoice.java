package com.paweu.autofleet.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Invoice {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String date;
    private String invoiceNumber;
    private float total;
    private Binary invoiceBinary;
    private Collection<InvoicePos> positions;
}
