package com.paweu.autofleet.event;


import com.paweu.autofleet.event.request.RequestNewEvent;
import com.paweu.autofleet.event.request.RequestUpdateEvent;
import com.paweu.autofleet.event.response.ResponseEvent;
import com.paweu.autofleet.event.response.ResponseEventDetails;
import com.paweu.autofleet.event.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/event")
public class EventControllerImpl implements EventController {
    private final EventService eventService;

    @Override
    public Mono<ResponseEntity<ResponseEvent>> addEvent(@Valid RequestNewEvent newEvent) {
        return eventService.addNewEvent(newEvent);
    }

    @Override
    public Mono<ResponseEntity<ResponseEventDetails>> getEvent(UUID id) {
        return eventService.getEvent(id);
    }

    @Override
    public Mono<ResponseEntity<ResponseEvent>> updateEvent(UUID id,
                                                           @Valid RequestUpdateEvent updateEventData) {
        return eventService.updateEvent(id, updateEventData);
    }
}
