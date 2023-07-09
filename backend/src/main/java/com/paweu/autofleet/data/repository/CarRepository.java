package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.Car;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;


@Repository
public interface CarRepository extends R2dbcRepository<Car,UUID> {
    Flux<Car> findAllByUserId(UUID userId);
}