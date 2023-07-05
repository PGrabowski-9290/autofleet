package com.paweu.autofleet.cars.service;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.cars.response.ResponseDeleted;
import com.paweu.autofleet.data.models.Car;
import com.paweu.autofleet.data.repository.CarRepository;
import com.paweu.autofleet.security.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
public class CarsService {
    @Autowired
    private CarRepository carRepository;

    public Mono<ResponseEntity<ResponseCarsList>> getListCars(SecurityUserDetails user) {
        return carRepository.findAllByUserId(user.getId())
                .collectList()
                .map(list -> ResponseEntity.ok().body(new ResponseCarsList("pobrano", list)));
    }

    public Mono<ResponseEntity<ResponseCar>> addCar(RequestCarData reqNewCar, SecurityUserDetails auth) {
        Car car = Car.fromRequestNew(reqNewCar);
        car.setUserId(auth.getId());
        return carRepository.save(car)
                .map(it -> ResponseEntity.ok().body(it.toResponseCar()));
    }


    public Mono<ResponseEntity<ResponseCar>> getCar(Optional<UUID> carId) {
        return carId.map(s -> carRepository.findById(s)
                .map(it -> ResponseEntity.ok().body(it.toResponseCar()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))).orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }

    public Mono<ResponseEntity<ResponseDeleted>> deleteCar(Optional<UUID> carId) {
        return carId.map(s -> carRepository.deleteById(s)
                        .then(Mono.fromCallable(() -> ResponseEntity.ok().body(new ResponseDeleted(true)))))  //to coś to trzeba poprawić
                .orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }

    public Mono<ResponseEntity<ResponseCar>> updateCar(Optional<UUID> carId, RequestCarData reqCar, SecurityUserDetails auth) {
        return carId.map(s -> carRepository.findById(s)
                        .flatMap(car -> {
                            car.update(reqCar);
                            car.setUserId(auth.getId());
                            return carRepository.save(car);
                        })
                        .map(it -> ResponseEntity.ok().body(it.toResponseCar()))
                        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())))
                .orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
