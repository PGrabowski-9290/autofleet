package com.paweu.autofleet.cars;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.cars.response.ResponseDeleted;
import com.paweu.autofleet.data.models.Car;
import com.paweu.autofleet.data.service.CarServiceDb;
import com.paweu.autofleet.security.SecurityUserDetails;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
@RequestMapping("/car")
public class CarsController {

    private final CarServiceDb carServiceDb;

    public CarsController(CarServiceDb carServiceDb) {
        this.carServiceDb = carServiceDb;
    }

    @GetMapping("/list")
    public Mono<ResponseEntity<ResponseCarsList>> getListCars(@CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth){
        return carServiceDb.getCarsList(auth.getId())
                .collectList()
                .map(list -> ResponseEntity.ok().body(new ResponseCarsList("pobrano", list)));
    }

    @PostMapping("/add")
    public Mono<ResponseEntity<ResponseCar>> addNewCar(@CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth,
                                             @RequestBody RequestCarData reqNewCar){

        Car car = Car.fromRequestNew(reqNewCar);
        car.setUserId(new ObjectId(auth.getId()));
        return carServiceDb.addCar(car)
                .map(it -> ResponseEntity.ok().body(it.toResponseCar()));
    }

    @GetMapping(value = {"/","/{id}"})
    public Mono<ResponseEntity<ResponseCar>> getCar(@PathVariable(name = "id") Optional<String> carId){
        return carId.map(s -> carServiceDb.getCar(s)
                .map(it -> ResponseEntity.ok().body(it.toResponseCar()))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))).orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @DeleteMapping(value = {"/", "/{id}"})
    public Mono<ResponseEntity<ResponseDeleted>> deleteCar(@PathVariable(name = "id") Optional<String> carId){
        return carId.map(s -> carServiceDb.deleteCar(s)
                .map(it -> ResponseEntity.ok().body(new ResponseDeleted(it.getDeletedCount() == 1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()))).orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @PutMapping(value = {"/", "/{id}"})
    public Mono<ResponseEntity<ResponseCar>> updateCar(@PathVariable(name = "id") Optional<String> carId,
                                                       @RequestBody RequestCarData reqCar,
                                                       @CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth){
        Car updateCar = Car.fromRequestNew(reqCar);
        updateCar.setUserId(new ObjectId(auth.getId()));
        return carId.map(s -> carServiceDb.updateCar(s,updateCar)
                        .map(it -> ResponseEntity.ok().body(it.toResponseCar()))
                        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build())))
                .orElseGet(() -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
