package com.paweu.autofleet.data.service;

import com.mongodb.client.result.DeleteResult;
import com.paweu.autofleet.data.models.Car;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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


    public Mono<Car> getCar(String id) {
        return mongoTemplate.findOne(Query.query(where("_id").is(id)),Car.class);
    }

    public Mono<DeleteResult> deleteCar(String id) {
        return mongoTemplate.remove(Query.query(where("_id").is(id)), Car.class);
    }

    public Mono<Car> updateCar(String id, Car car){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        query.addCriteria(Criteria.where("userId").is(car.getUserId()));
        return mongoTemplate.findOne(query, Car.class)
                .map(c -> {
                    car.setId(c.getId());
                    return car;
                }).flatMap(car1 -> mongoTemplate.save(car));
    }
}
