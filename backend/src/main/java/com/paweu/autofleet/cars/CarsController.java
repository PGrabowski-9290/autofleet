package com.paweu.autofleet.cars;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.cars.response.ResponseDeleted;
import com.paweu.autofleet.cars.response.ResponseUpdate;
import com.paweu.autofleet.cars.service.CarsService;
import com.paweu.autofleet.security.SecurityUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/car")
public class CarsController {
    @Autowired
    private CarsService carsService;

    @GetMapping("/")
    public Mono<ResponseEntity<ResponseCarsList>> getListCars(@CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth){
        return carsService.getListCars(auth);
    }

    @PostMapping("/")
    public Mono<ResponseEntity<ResponseCar>> addNewCar(@CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth,
                                             @RequestBody @Valid RequestCarData reqNewCar){
        return carsService.addCar(reqNewCar, auth);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ResponseCar>> getCar(@PathVariable(name = "id") Optional<UUID> carId){
        return carsService.getCar(carId);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<ResponseDeleted>> deleteCar(@PathVariable(name = "id") Optional<UUID> carId){
        return carsService.deleteCar(carId);
    }

    @PatchMapping("/{id}")
    public Mono<ResponseEntity<ResponseUpdate>> updateCar(@PathVariable(name = "id") Optional<UUID> carId,
                                                          @RequestBody @Valid RequestCarData reqCar,
                                                          @CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth){
        return carsService.updateCar(carId, reqCar, auth);
    }
}
