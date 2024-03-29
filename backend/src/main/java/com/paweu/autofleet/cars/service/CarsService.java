package com.paweu.autofleet.cars.service;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.cars.response.ResponseDeleted;
import com.paweu.autofleet.cars.response.ResponseUpdate;
import com.paweu.autofleet.data.models.Car;
import com.paweu.autofleet.data.models.Event;
import com.paweu.autofleet.data.repository.CarRepository;
import com.paweu.autofleet.error.exceptions.CarNotFoundException;
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
                .map(Car::toResponseCar)
                .collectList()
                .map(list -> ResponseEntity.ok().body(new ResponseCarsList("pobrano", list)));
    }

    public Mono<ResponseEntity<ResponseCar>> getCar(UUID carId) {
        return carRepository.findById(carId)
                .map(Car::toResponseCar)
                .map(it -> ResponseEntity.ok().body(it))
                .switchIfEmpty(Mono.error(CarNotFoundException::new));
    }

    public Mono<ResponseEntity<ResponseCar>> addCar(RequestCarData reqNewCar, SecurityUserDetails auth) {
        Car newCar = Car.builder()
                .brand(reqNewCar.brand())
                .model(reqNewCar.model())
                .year(reqNewCar.year())
                .carType(reqNewCar.carType())
                .engineType(reqNewCar.engineType())
                .engine(reqNewCar.engine())
                .odometer(reqNewCar.odometer())
                .numberPlate(reqNewCar.numberPlate())
                .build();
        newCar.setUserId(auth.getId());
        return carRepository.save(newCar)
                .map(it -> ResponseEntity.ok().body(it.toResponseCar()));
    }

    public Mono<ResponseEntity<ResponseDeleted>> deleteCar(UUID carId) {
        return carRepository.deleteById(carId)
                        .map(res -> ResponseEntity.ok().body(new ResponseDeleted(res)))
                        .switchIfEmpty(Mono.error(CarNotFoundException::new));
    }

    public Mono<ResponseEntity<ResponseUpdate>> updateCar(UUID carId, RequestCarData reqCar,
                                                          SecurityUserDetails auth) {
        return carRepository.findById(carId)
                        .flatMap(car -> {
                            car.setBrand(reqCar.brand());
                            car.setModel(reqCar.model());
                            car.setYear(reqCar.year());
                            car.setCarType(reqCar.carType());
                            car.setEngine(reqCar.engine());
                            car.setEngineType(reqCar.engineType());
                            car.setOdometer(reqCar.odometer());
                            car.setNumberPlate(reqCar.numberPlate());
                            car.setUserId(auth.getId());
                            return carRepository.update(car);
                        })
                        .map(it -> ResponseEntity.ok().body(new ResponseUpdate(it)))
                        .switchIfEmpty(Mono.error(CarNotFoundException::new));
    }
}
