package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.InvoicePos;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface InvoicePosRepository extends R2dbcRepository<InvoicePos, UUID> {

    public Mono<Void> deleteAllByInvoiceId(UUID id);
}
