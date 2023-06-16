package com.paweu.autofleet.cars;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.cars.response.ResponseDeleted;
import com.paweu.autofleet.cars.service.CarsService;
import com.paweu.autofleet.security.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/car")
public class CarsController {
    @Autowired
    private CarsService carsService;

    @GetMapping("/list")
    public Mono<ResponseEntity<ResponseCarsList>> getListCars(@CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth){
        return carsService.getListCars(auth);
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<ResponseCar>> addNewCar(@CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth,
                                             @RequestBody RequestCarData reqNewCar){
        return carsService.addCar(reqNewCar, auth);
    }

    @GetMapping(value = {"/","/{id}"})
    public Mono<ResponseEntity<ResponseCar>> getCar(@PathVariable(name = "id") Optional<String> carId){
        return carsService.getCar(carId);
    }

    @DeleteMapping(value = {"/", "/{id}"})
    public Mono<ResponseEntity<ResponseDeleted>> deleteCar(@PathVariable(name = "id") Optional<String> carId){
        return carsService.deleteCar(carId);
    }

    @PutMapping(value = {"/", "/{id}"})
    public Mono<ResponseEntity<ResponseCar>> updateCar(@PathVariable(name = "id") Optional<String> carId,
                                                       @RequestBody RequestCarData reqCar,
                                                       @CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth){
        return carsService.updateCar(carId, reqCar, auth);
    }
}
