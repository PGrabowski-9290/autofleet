package com.paweu.autofleet.cars;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.cars.response.ResponseDeleted;
import com.paweu.autofleet.cars.response.ResponseUpdate;
import com.paweu.autofleet.cars.service.CarsService;
import com.paweu.autofleet.security.SecurityUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/car")
public class CarsControllerImpl implements CarsController {
    private final CarsService carsService;

    @Override
    public Mono<ResponseEntity<ResponseCarsList>> getListCars(SecurityUserDetails auth) {
        return carsService.getListCars(auth);
    }

    @Override
    public Mono<ResponseEntity<ResponseCar>> addNewCar(SecurityUserDetails auth,
                                                       @Valid RequestCarData reqNewCar) {
        return carsService.addCar(reqNewCar, auth);
    }

    @Override
    public Mono<ResponseEntity<ResponseCar>> getCar(UUID carId) {
        return carsService.getCar(carId);
    }

    @Override
    public Mono<ResponseEntity<ResponseDeleted>> deleteCar(UUID carId) {
        return carsService.deleteCar(carId);
    }

    @Override
    public Mono<ResponseEntity<ResponseUpdate>> updateCar(UUID carId,
                                                          @Valid RequestCarData reqCar,
                                                          SecurityUserDetails auth) {
        return carsService.updateCar(carId, reqCar, auth);
    }
}
