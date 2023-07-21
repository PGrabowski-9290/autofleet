package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.Car;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Component
public interface CarRepository {
    Flux<Car> findAllByUserId(UUID userId);
    Mono<Car> findById(UUID carId);
    Mono<Car> save(Car car);
    Mono<Long> deleteById(UUID s);

    Mono<Long> update(Car car);
}
