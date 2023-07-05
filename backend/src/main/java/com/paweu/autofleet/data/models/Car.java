package com.paweu.autofleet.data.models;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
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
    private Timestamp lastUpdate = new Timestamp(new Date().getTime());

    public static Car fromRequestNew(RequestCarData req){
        Car car = new Car();
        car.setBrand(req.brand());
        car.setModel(req.model());
        car.setYear(req.year());
        car.setCarType(req.carType());
        car.setEngineType(req.engineType());
        car.setEngineSize(req.engineSize());
        car.setOdometer(req.odometer());
        car.setNumberPlate(req.numberPlate());
        return car;
    }

    public Car update(RequestCarData carData){
        setBrand(carData.brand());
        setModel(carData.model());
        setYear(carData.year());
        setCarType(carData.carType());
        setEngineType(carData.engineType());
        setEngineSize(carData.engineSize());
        setOdometer(carData.odometer());
        setLastUpdate(new Timestamp(new Date().getTime()));

        return this;
    }

    public ResponseCar toResponseCar(){
        return new ResponseCar(id.toString(), userId.toString(), brand, model, year, carType, engineType,
                engineSize, odometer, numberPlate, lastUpdate);
    }
}
