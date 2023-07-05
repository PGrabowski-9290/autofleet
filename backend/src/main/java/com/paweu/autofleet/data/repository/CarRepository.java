package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Repository
public interface CarRepository extends ReactiveCrudRepository<Car,UUID> {

    Flux<Car> findAllByUserId(UUID userId);

}
