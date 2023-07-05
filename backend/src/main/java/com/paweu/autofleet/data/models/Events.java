package com.paweu.autofleet.data.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Table("public.event")
public class Events {
    @Id @Column("event_id")
    private UUID id;
    @Column("event_date")
    private LocalDate date;
    @Column("last_update")
    private Timestamp lastUpdate;
    @Column("odometer")
    private int odometer;
    @Column("oil")
    private boolean oil;
    @Column("oil_filter")
    private boolean oilFilter;
    @Column("air_filter")
    private boolean airFilter;
    @Column("timming_belt_kit")
    private boolean timingBeltKit;
    @Column("description")
    private String description;
}
