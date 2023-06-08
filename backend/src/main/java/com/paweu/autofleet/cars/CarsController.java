package com.paweu.autofleet.cars;

import com.paweu.autofleet.cars.request.RequestNewCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.data.models.Car;
import com.paweu.autofleet.data.service.CarServiceDb;
import com.paweu.autofleet.security.AuthFilter;
import com.paweu.autofleet.security.SecurityUserDetails;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/car")
public class CarsController {

    private CarServiceDb carServiceDb;

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
    public Mono<ResponseEntity<?>> addNewCar(@CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails auth,
                                             @RequestBody RequestNewCar reqNewCar){

        Car car = Car.fromRequestNew(reqNewCar);
        car.setUserId(new ObjectId(auth.getId()));
        return carServiceDb.addCar(car)
                .map(it -> ResponseEntity.ok().body(it));
    }

}
