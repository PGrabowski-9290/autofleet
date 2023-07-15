package com.paweu.autofleet.data.models;

import com.paweu.autofleet.event.response.ResponseEvent;

import com.paweu.autofleet.event.response.ResponseEventDetails;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    @Transient
    @Builder.Default
    private List<Invoice> invoices = new ArrayList<>();

    public static Mono<Event> fromRows(List<List<Map<String,Object>>> rows){
        return Mono.just(Event.builder()
                .id(UUID.fromString(rows.get(0).get(0).get("e_event_id").toString()))
                .carId(UUID.fromString(rows.get(0).get(0).get("e_car_id").toString()))
                .date(LocalDate.parse(rows.get(0).get(0).get("e_event_date").toString()))
                .lastUpdate((LocalDateTime) rows.get(0).get(0).get("e_last_update"))
                .odometer((Integer) rows.get(0).get(0).get("e_odometer"))
                .oil((Boolean) rows.get(0).get(0).get("e_oil"))
                .oilFilter((Boolean) rows.get(0).get(0).get("e_oil_filter"))
                .airFilter((Boolean) rows.get(0).get(0).get("e_air_filter"))
                .timingBeltKit((Boolean) rows.get(0).get(0).get("e_timing_belt_kit"))
                .description(rows.get(0).get(0).get("e_description").toString())
                .invoices(rows.stream()
                        .map(Invoice::fromRows)
                        .filter(Objects::nonNull)
                        .toList())
                .build());
    }

    public static Event fromRow(Map<String, Object> row) {
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

    public ResponseEventDetails toResponseEventDetails(){
        return new ResponseEventDetails(this.id, this.carId, this.date, this.lastUpdate, this.odometer, this.oil,
                this.oilFilter, this.airFilter, this.timingBeltKit, this.description, this.invoices);
    }
}
