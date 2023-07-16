package com.paweu.autofleet.data.repository;

import org.springframework.stereotype.Component;
import com.paweu.autofleet.data.models.Invoice;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public interface InvoiceRepository {
    public Mono<Invoice> findById(UUID id);

    public Flux<Invoice> findAllByUserId(UUID id);
    public Mono<Invoice> save(Invoice invoice);

    public Mono<Invoice> deleteById(UUID id);
}
