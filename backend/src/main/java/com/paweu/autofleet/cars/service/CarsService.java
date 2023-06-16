package com.paweu.autofleet.cars.service;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.cars.response.ResponseDeleted;
import com.paweu.autofleet.data.models.Car;
import com.paweu.autofleet.data.repository.CarRepository;
import com.paweu.autofleet.security.SecurityUserDetails;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class CarsService {
    @Autowired
    private CarRepository carRepository;

    public Mono<ResponseEntity<ResponseCarsList>> getListCars(SecurityUserDetails user) {
        return carRepository.getCarsList(user.getId())
                .collectList()
                .map(list -> ResponseEntity.ok().body(new ResponseCarsList("pobrano", list)));
    }

    public Mono<ResponseEntity<ResponseCar>> addCar(RequestCarData reqNewCar, SecurityUserDetails auth) {
        Car car = Car.fromRequestNew(reqNewCar);
        car.setUserId(new ObjectId(auth.getId()));
        return carRepository.addCar(car)
                .map(it -> ResponseEntity.ok().body(it.toResponseCar()));
    }


    public Mono<ResponseEntity<ResponseCar>> getCar(Optional<String> carId) {
        return carId.map(s -> carRepository.getCar(s)
                .map(it -> ResponseEntity.ok().body(it.toResponseCar()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))).orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }

    public Mono<ResponseEntity<ResponseDeleted>> deleteCar(Optional<String> carId) {
        return carId.map(s -> carRepository.deleteCar(s)
                .map(it -> ResponseEntity.ok().body(new ResponseDeleted(it.getDeletedCount() == 1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))).orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }

    public Mono<ResponseEntity<ResponseCar>> updateCar(Optional<String> carId, RequestCarData reqCar, SecurityUserDetails auth) {
        Car updateCar = Car.fromRequestNew(reqCar);
        updateCar.setUserId(new ObjectId(auth.getId()));
        return carId.map(s -> carRepository.updateCar(s,updateCar)
                        .map(it -> ResponseEntity.ok().body(it.toResponseCar()))
                        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())))
                .orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
