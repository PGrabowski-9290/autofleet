package com.paweu.autofleet.data.models;

import com.paweu.autofleet.event.response.ResponseEvent;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Table("public.event")
public class Event {
    @Id @Column("event_id")
    private UUID id;
    @Column("car_id")
    private UUID carId;
    @Column("event_date")
    private LocalDate date;
    @Column("last_update")
    @Builder.Default
    private Timestamp lastUpdate = new Timestamp(new Date().getTime());
    @Column("odometer")
    private Integer odometer;
    @Column("oil")
    @Builder.Default
    private boolean oil = false;
    @Column("oil_filter")
    @Builder.Default
    private boolean oilFilter = false;
    @Column("air_filter")
    @Builder.Default
    private boolean airFilter = false;
    @Column("timing_belt_kit")
    @Builder.Default
    private boolean timingBeltKit = false;
    @Column("description")
    @Builder.Default
    private String description = "";

    public ResponseEvent toResponseEvent(){
        return new ResponseEvent(this.id, this.carId, this.date, this.lastUpdate, this.odometer, this.oil,
                this.oilFilter, this.airFilter, this.timingBeltKit, this.description);
    }
}
