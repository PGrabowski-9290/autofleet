package com.paweu.autofleet.event;

import com.paweu.autofleet.event.request.RequestNewEvent;
import com.paweu.autofleet.event.request.RequestUpdateEvent;
import com.paweu.autofleet.event.response.ResponseEvent;
import com.paweu.autofleet.event.response.ResponseEventDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RestController
@RequestMapping("/event")
@Tag(name = "Car events")
public interface EventController {
    @PostMapping("/")
    Mono<ResponseEntity<ResponseEvent>> addEvent(@RequestBody @Valid RequestNewEvent newEvent);

    @GetMapping("/{id}")
    Mono<ResponseEntity<ResponseEventDetails>> getEvent(@PathVariable UUID id);

    @PutMapping("/{id}")
    Mono<ResponseEntity<ResponseEvent>> updateEvent(@PathVariable UUID id,
                                                    @RequestBody @Valid RequestUpdateEvent updateEventData);
}
