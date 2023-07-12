package com.paweu.autofleet.data.models;

import com.paweu.autofleet.event.response.ResponseEvent;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private LocalDateTime lastUpdate = LocalDateTime.now();
    @Column("odometer")
    private Integer odometer;
    @Column("oil")
    private boolean oil;
    @Column("oil_filter")
    private boolean oilFilter;
    @Column("air_filter")
    private boolean airFilter;
    @Column("timing_belt_kit")
    private boolean timingBeltKit;
    @Column("description")
    @Builder.Default
    private String description = "";

    public static Event fromRow(Map<String,Object> row){
        if (row.get("event_id") != null){
            return Event.builder()
                    .id(UUID.fromString(row.get("event_id").toString()))
                    .carId(UUID.fromString(row.get("car_id").toString()))
                    .date(LocalDate.parse(row.get("event_date").toString()))
                    .lastUpdate((LocalDateTime) row.get("last_update"))
                    .odometer((Integer) row.get("odometer"))
                    .oil((Boolean) row.get("oil"))
                    .oilFilter((Boolean) row.get("oil_filter"))
                    .airFilter((Boolean) row.get("air_filter"))
                    .timingBeltKit((Boolean) row.get("timing_belt_kit"))
                    .description(row.get("description").toString())
                    .build();
        } else {
          return null;
        }
    }

    public static Event fromCarRow(Map<String, Object> row) {
        if (row.get("e_event_id") != null){
            return Event.builder()
                    .id(UUID.fromString(row.get("e_event_id").toString()))
                    .carId(UUID.fromString(row.get("e_car_id").toString()))
                    .date(LocalDate.parse(row.get("e_event_date").toString()))
                    .lastUpdate((LocalDateTime) row.get("e_last_update"))
                    .odometer((Integer) row.get("e_odometer"))
                    .oil((Boolean) row.get("e_oil"))
                    .oilFilter((Boolean) row.get("e_oil_filter"))
                    .airFilter((Boolean) row.get("e_air_filter"))
                    .timingBeltKit((Boolean) row.get("e_timing_belt_kit"))
                    .description(row.get("e_description").toString())
                    .build();
        } else {
            return null;
        }
    }

    public ResponseEvent toResponseEvent(){
        return new ResponseEvent(this.id, this.carId, this.date, this.lastUpdate, this.odometer, this.oil,
                this.oilFilter, this.airFilter, this.timingBeltKit, this.description);
    }
}
