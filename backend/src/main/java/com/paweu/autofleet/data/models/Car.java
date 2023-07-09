package com.paweu.autofleet.data.models;

import com.paweu.autofleet.cars.response.ResponseCar;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    private int year;
    @Column("car_type")
    private String carType;
    @Column("engine_type")
    private String engineType;
    @Column("engine_size")
    private String engineSize;
    @Column("odometer")
    private int odometer;
    @Column("number_plate")
    private String numberPlate;
    @Column("last_update")
    @Builder.Default
    private Timestamp lastUpdate = new Timestamp(new Date().getTime());
    @Transient
    private List<UUID> carEvents;

    public ResponseCar toResponseCar(){
        return new ResponseCar(id.toString(), userId.toString(), brand, model, year, carType, engineType,
                engineSize, odometer, numberPlate, lastUpdate);
    }
}
