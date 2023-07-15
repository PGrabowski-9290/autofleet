package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.Event;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public interface MyEventRepository {
    public Mono<Event> findEventJoinInvoiceJoinInvoicePosByEventId(UUID eventId);
}
