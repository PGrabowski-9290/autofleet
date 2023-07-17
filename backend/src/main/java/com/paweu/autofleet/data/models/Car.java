package com.paweu.autofleet.data.models;

import com.paweu.autofleet.cars.response.ResponseCar;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "public.car")
public class Car {
    @Id @Column("car_id")
    private UUID id;
    @Column("user_id")
    private UUID userId;
    @Column("brand")
    private String brand;
    @Column("model")
    private String model;
    @Column("car_year")
    private Integer year;
    @Column("car_type")
    private String carType;
    @Column("engine_type")
    private String engineType;
    @Column("engine")
    private String engine;
    @Column("odometer")
    private Integer odometer;
    @Column("number_plate")
    private String numberPlate;
    @Column("last_update")
    @Builder.Default
    private LocalDateTime lastUpdate = LocalDateTime.now();
    @Transient
    @Builder.Default
    private List<Event> carEvents = new ArrayList<>();

    public static Mono<Car> fromRows(List<Map<String, Object>> rows){
        return Mono.just(Car.builder()
                .id(UUID.fromString(rows.get(0).get("c_car_id").toString()))
                .userId(UUID.fromString(rows.get(0).get("c_user_id").toString()))
                .brand((String) rows.get(0).get("c_brand"))
                .model((String) rows.get(0).get("c_model"))
                .year((Integer) rows.get(0).get("c_car_year"))
                .carType((String) rows.get(0).get("c_car_type"))
                .engineType((String) rows.get(0).get("c_engine_type"))
                .engine((String) rows.get(0).get("c_engine"))
                .odometer((Integer) rows.get(0).get("c_odometer"))
                .numberPlate((String) rows.get(0).get("c_number_plate"))
                .lastUpdate((LocalDateTime) rows.get(0).get("c_last_update"))
                        .carEvents(rows.stream()
                                .map(Event::fromRow)
                                .filter(Objects::nonNull)
                                .toList())
                .build()
        );

    }


    public ResponseCar toResponseCar(){
        return new ResponseCar(id.toString(), userId.toString(), brand, model, year, carType, engineType,
                engine, odometer, numberPlate, lastUpdate, carEvents);
    }
}
