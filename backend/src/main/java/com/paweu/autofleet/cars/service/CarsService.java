package com.paweu.autofleet.cars.service;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.*;
import com.paweu.autofleet.data.models.Car;
import com.paweu.autofleet.data.repository.CarRepository;
import com.paweu.autofleet.security.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CarsService {
    private final CarRepository carRepository;

    public Mono<ResponseEntity<ResponseCarsList>> getListCars(SecurityUserDetails user) {
        return carRepository.findAllByUserId(user.getId())
                .collectList()
                .map(list -> ResponseEntity.ok().body(new ResponseCarsList("pobrano", list)));
    }

    public Mono<ResponseEntity<ResponseCar>> addCar(RequestCarData reqNewCar, SecurityUserDetails auth) {
        Car newCar = Car.builder()
                .brand(reqNewCar.brand())
                .model(reqNewCar.model())
                .year(reqNewCar.year())
                .carType(reqNewCar.carType())
                .engineType(reqNewCar.engineType())
                .engineSize(reqNewCar.engineSize())
                .odometer(reqNewCar.odometer())
                .numberPlate(reqNewCar.numberPlate())
                        .build();
        newCar.setUserId(auth.getId());
        return carRepository.save(newCar)
                .map(it -> ResponseEntity.ok().body(it.toResponseCar()));
    }


    public Mono<ResponseEntity<ResponseCar>> getCar(Optional<UUID> carId) {
        return carId.map(s -> carRepository.findById(s)
                .map(it -> ResponseEntity.ok().body(it.toResponseCar()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))).orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }

    public Mono<ResponseEntity<ResponseDeleted>> deleteCar(Optional<UUID> carId) {
        return carId.map(s -> carRepository.deleteById(s)
                        .map(res -> ResponseEntity.ok().body(new ResponseDeleted(res)))
                        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())))
                    .orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }

    public Mono<ResponseEntity<ResponseUpdate>> updateCar(Optional<UUID> carId, RequestCarData reqCar,
                                                          SecurityUserDetails auth) {
        return carId.map(s -> carRepository.findById(s)
                        .flatMap(car -> {
                            car.setBrand(reqCar.brand());
                            car.setModel(reqCar.model());
                            car.setYear(reqCar.year());
                            car.setCarType(reqCar.carType());
                            car.setEngineSize(reqCar.engineSize());
                            car.setEngineType(reqCar.engineType());
                            car.setOdometer(reqCar.odometer());
                            car.setNumberPlate(reqCar.numberPlate());
                            car.setUserId(auth.getId());
                            return carRepository.update(car);
                        })
                        .map(it -> ResponseEntity.ok().body(new ResponseUpdate(it)))
                        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())))
                .orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
