package com.paweu.autofleet.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    private String mark;
    private String model;
    private int year;
    private String category;
    private String engine;
    private int odometer;
    private String numberPlate;
    private Collection<Events> events;
}
