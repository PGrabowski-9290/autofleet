package com.paweu.autofleet.cars;

import com.paweu.autofleet.cars.request.RequestCarData;
import com.paweu.autofleet.cars.response.ResponseCar;
import com.paweu.autofleet.cars.response.ResponseCarsList;
import com.paweu.autofleet.cars.response.ResponseDeleted;
import com.paweu.autofleet.cars.response.ResponseUpdate;
import com.paweu.autofleet.security.SecurityUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public interface CarsController {
    @Operation(
            description = "Get list of user cars",
            summary = "Get user cars"
    )
    @GetMapping("/")
    Mono<ResponseEntity<ResponseCarsList>> getListCars(@AuthenticationPrincipal SecurityUserDetails auth);

    @Operation(
            description = "Get full car information and list of car events by provided car id",
            summary = "Get car"
    )
    @GetMapping("/{id}")
    Mono<ResponseEntity<ResponseCar>> getCar(@PathVariable(name = "id") Optional<UUID> carId);

    @Operation(
            description = "Add new car to user list",
            summary = "Add car"
    )
    @PostMapping("/")
    Mono<ResponseEntity<ResponseCar>> addNewCar(@AuthenticationPrincipal SecurityUserDetails auth,
                                                @RequestBody @Valid RequestCarData reqNewCar);

    @Operation(
            description = "Delete car by provided id",
            summary = "Delete car"
    )
    @DeleteMapping("/{id}")
    Mono<ResponseEntity<ResponseDeleted>> deleteCar(@PathVariable(name = "id") Optional<UUID> carId);

    @Operation(
            description = "Update car",
            summary = "Update car"
    )
    @PatchMapping("/{id}")
    Mono<ResponseEntity<ResponseUpdate>> updateCar(@PathVariable(name = "id") Optional<UUID> carId,
                                                   @RequestBody @Valid RequestCarData reqCar,
                                                   @AuthenticationPrincipal SecurityUserDetails auth);
}
