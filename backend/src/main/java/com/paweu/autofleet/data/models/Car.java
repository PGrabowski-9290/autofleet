package com.paweu.autofleet.data.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.paweu.autofleet.cars.request.RequestNewCar;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

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

    public String toString(){
        return "[ id: "+id+", userId: "+userId+", brand: "+brand+", model: "+model+", year: "+String.valueOf(year)+", category: "+
                category+", engine: "+engine+", odometer: "+String.valueOf(odometer)+", numberPlate: "+numberPlate;
    }
}
