package com.paweu.autofleet.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.paweu.autofleet.cars.request.RequestNewCar;
import com.paweu.autofleet.cars.response.ResponseCar;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "cars")
public class Car {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId userId;
    private String brand;
    private String model;
    private int year;
    private String category;
    private String engine;
    private int odometer;
    private String numberPlate;
    private Collection<Events> events;

    public static Car fromRequestNew(RequestNewCar req){
        Car car = new Car();
        car.setBrand(req.brand());
        car.setModel(req.model());
        car.setYear(req.year());
        car.setCategory(req.category());
        car.setEngine(req.engine());
        car.setOdometer(req.odometer());
        car.setNumberPlate(req.numberPlate());

        return car;
    }

    public ResponseCar toResponseCar(){
        return new ResponseCar(id, userId.toString(), brand, model, year, category,engine,
                odometer, numberPlate);
    }

    public String toString(){
        return "[ id: "+id+", userId: "+userId+", brand: "+brand+", model: "+model+", year: "+year+", category: "+
                category+", engine: "+engine+", odometer: "+odometer+", numberPlate: "+numberPlate;
    }
}
