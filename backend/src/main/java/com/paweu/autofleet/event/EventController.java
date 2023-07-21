package com.paweu.autofleet.event;

import com.paweu.autofleet.event.request.RequestNewEvent;
import com.paweu.autofleet.event.request.RequestUpdateEvent;
import com.paweu.autofleet.event.response.ResponseEvent;
import com.paweu.autofleet.event.response.ResponseEventDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RestController
@RequestMapping("/event")
@Tag(name = "Car events")
@SecurityRequirement(name = "bearer-key")
public interface EventController {
    @Operation(
            description = "Add new event to car",
            summary = "Add new event"
    )
    @PostMapping("/")
    Mono<ResponseEntity<ResponseEvent>> addEvent(@RequestBody @Valid RequestNewEvent newEvent);

    @Operation(
            description = "Get event details with invoice list assigned to event",
            summary = "Get event details"
    )
    @GetMapping("/{id}")
    Mono<ResponseEntity<ResponseEventDetails>> getEvent(@PathVariable UUID id);

    @Operation(
            description = "Update event",
            summary = "Update event"
    )
    @PutMapping("/{id}")
    Mono<ResponseEntity<ResponseEvent>> updateEvent(@PathVariable UUID id,
                                                    @RequestBody @Valid RequestUpdateEvent updateEventData);
}
