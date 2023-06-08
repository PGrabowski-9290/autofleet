package com.paweu.autofleet.data.service;

import com.paweu.autofleet.data.models.Car;
import com.paweu.autofleet.data.models.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class CarServiceDb {
    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    public Flux<Car> getCarsList(String userId){
        return mongoTemplate.find(Query.query(where("userId").is(new ObjectId(userId))), Car.class);
    }

    public Mono<Car> addCar(Car car){
        return mongoTemplate.save(car, "cars");
    }

}
