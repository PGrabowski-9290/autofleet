package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.Invoice;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public interface InvoiceRepository {
    Mono<Invoice> findById(UUID id);

    Flux<Invoice> findAllByUserId(UUID id);

    Mono<Invoice> save(Invoice invoice);

    Mono<Long> deleteById(UUID id);
}
