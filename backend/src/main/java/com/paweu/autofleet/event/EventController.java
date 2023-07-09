package com.paweu.autofleet.event;


import com.paweu.autofleet.data.models.Event;
import com.paweu.autofleet.event.request.RequestNewEvent;
import com.paweu.autofleet.event.request.RequestUpdateEvent;
import com.paweu.autofleet.event.response.ResponseEvent;
import com.paweu.autofleet.event.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;
    @PostMapping("/")
    public Mono<ResponseEntity<ResponseEvent>> addEvent(@RequestBody @Valid RequestNewEvent newEvent){
        return eventService.addNewEvent(new Event(newEvent));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ResponseEvent>> getEvent(@PathVariable UUID id){
        return eventService.getEvent(id);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ResponseEvent>> updateEvent(@PathVariable UUID id,
                                                   @RequestBody @Valid RequestUpdateEvent updateEventData){
        return eventService.updateEvent(id, updateEventData);
    }
}
