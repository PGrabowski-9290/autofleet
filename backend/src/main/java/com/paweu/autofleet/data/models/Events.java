package com.paweu.autofleet.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Events {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String date;
    private int odometer;
    private Collection<String> list;
    private String description;
    private Collection<Invoice> invoices;
}
