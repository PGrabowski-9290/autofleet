package com.paweu.autofleet.event.service;

import com.paweu.autofleet.data.models.Event;
import com.paweu.autofleet.data.repository.EventRepository;
import com.paweu.autofleet.event.request.RequestNewEvent;
import com.paweu.autofleet.event.request.RequestUpdateEvent;
import com.paweu.autofleet.event.response.ResponseEvent;
import com.paweu.autofleet.event.response.ResponseEventDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    public Mono<ResponseEntity<ResponseEvent>> addNewEvent (@Valid RequestNewEvent requestNewEvent) {
        Event newEvent = Event.builder()
                .carId(requestNewEvent.carId())
                .date(requestNewEvent.eventDate())
                .odometer(requestNewEvent.odometer())
                .oil(requestNewEvent.oil())
                .oilFilter(requestNewEvent.oilFilter())
                .airFilter(requestNewEvent.airFilter())
                .timingBeltKit(requestNewEvent.timingBeltKit())
                .description(requestNewEvent.description())
                .build();
        return eventRepository.save(newEvent)
                .map(savedEvent -> ResponseEntity.ok().body(savedEvent.toResponseEvent()));
    }

    public Mono<ResponseEntity<ResponseEventDetails>> getEvent(UUID eventId){
        return eventRepository.findEventJoinInvoiceJoinInvoicePosByEventId(eventId)
                .map(event -> ResponseEntity.ok().body(event.toResponseEventDetails()));
    }

    public Mono<ResponseEntity<ResponseEvent>> updateEvent(UUID eventId, RequestUpdateEvent eventData){
        return eventRepository. findById(eventId)
                .map(event -> {
                    event.setDate(eventData.eventDate());
                    event.setLastUpdate(LocalDateTime.now());
                    event.setOdometer(eventData.odometer());
                    event.setOil(eventData.oil());
                    event.setOilFilter(eventData.oilFilter());
                    event.setAirFilter(eventData.airFilter());
                    event.setTimingBeltKit(eventData.timingBeltKit());
                    event.setDescription(eventData.description());
                    return event;
                })
                .flatMap(eventRepository::save)
                .map(updatedEvent -> ResponseEntity.ok().body(updatedEvent.toResponseEvent()));
    }

}
