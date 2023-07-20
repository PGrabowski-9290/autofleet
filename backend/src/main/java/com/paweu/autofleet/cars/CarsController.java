package com.paweu.autofleet.cars;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.cars.response.ResponseDeleted;
import com.paweu.autofleet.cars.response.ResponseUpdate;
import com.paweu.autofleet.security.SecurityUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/car")
@Tag(name = "Car")
public interface CarsController {
    @GetMapping("/")
    Mono<ResponseEntity<ResponseCarsList>> getListCars(@AuthenticationPrincipal SecurityUserDetails auth);

    @PostMapping("/")
    Mono<ResponseEntity<ResponseCar>> addNewCar(@AuthenticationPrincipal SecurityUserDetails auth,
                                                @RequestBody @Valid RequestCarData reqNewCar);

    @GetMapping("/{id}")
    Mono<ResponseEntity<ResponseCar>> getCar(@PathVariable(name = "id") Optional<UUID> carId);

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<ResponseDeleted>> deleteCar(@PathVariable(name = "id") Optional<UUID> carId);

    @PatchMapping("/{id}")
    Mono<ResponseEntity<ResponseUpdate>> updateCar(@PathVariable(name = "id") Optional<UUID> carId,
                                                   @RequestBody @Valid RequestCarData reqCar,
                                                   @AuthenticationPrincipal SecurityUserDetails auth);
}
