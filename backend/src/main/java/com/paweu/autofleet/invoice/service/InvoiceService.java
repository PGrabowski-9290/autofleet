package com.paweu.autofleet.invoice.service;

import com.paweu.autofleet.data.models.Invoice;
import com.paweu.autofleet.data.models.InvoicePos;
import com.paweu.autofleet.data.repository.InvoiceRepository;
import com.paweu.autofleet.invoice.request.RequestInvoice;
import com.paweu.autofleet.invoice.request.RequestInvoiceUpdate;
import com.paweu.autofleet.invoice.response.ResponseDeleted;
import com.paweu.autofleet.security.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public Mono<ResponseEntity<Invoice>> addInvoice(RequestInvoice requestNewInvoice,
                                                       SecurityUserDetails user){
        return Mono.just(
                Invoice.builder()
                    .userId(user.getId())
                    .eventId(requestNewInvoice.eventId())
                    .invoiceNumber(requestNewInvoice.invoiceNumber())
                    .date(requestNewInvoice.date())
                    .currency(requestNewInvoice.currency())
                    .invoicePosList(requestNewInvoice.invoicePosList().stream()
                            .map(req -> InvoicePos.builder()
                                    .name(req.name())
                                    .price(req.price())
                                    .quantity(req.qty())
                                    .build())
                            .toList())
                    .build())
                .flatMap(invoiceRepository::save)
                .map(it -> {
                    it.calcTotal();
                    return ResponseEntity.ok().body(it);
                });
    }

    public Mono<ResponseEntity<Invoice>> getInvoice(UUID id){
        return invoiceRepository.findById(id)
                .map(it -> ResponseEntity.ok().body(it));
    }

    public Mono<ResponseEntity<ResponseDeleted>> deleteInvoice(UUID id) {
        return invoiceRepository.deleteById(id)
                .map(it -> ResponseEntity.ok().body(new ResponseDeleted(it)))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found")));
    }

    public Mono<ResponseEntity<Invoice>> updateInvoice(UUID id, RequestInvoiceUpdate invoiceUpdate) {
        return invoiceRepository.findById(id)
                .map(invoice -> {
                    invoice.setLastUpdate(LocalDateTime.now());
                    invoice.setInvoicePosList(invoiceUpdate.invoicePosList()
                            .stream()
                            .map(requestInvoicePos ->InvoicePos.builder()
                                    .name(requestInvoicePos.name())
                                    .price(requestInvoicePos.price())
                                    .quantity(requestInvoicePos.qty())
                                    .build())
                            .toList());
                    invoice.setInvoiceNumber(invoiceUpdate.invoiceNumber());
                    invoice.setDate(invoiceUpdate.date());
                    invoice.setCurrency(invoiceUpdate.currency());
                    invoice.setEventId(invoiceUpdate.eventId());
                    return invoice;
                })
                .flatMap(invoiceRepository::save)
                .map(it -> ResponseEntity.ok().body(it));

    }

    public Mono<ResponseEntity<List<Invoice>>> getAllUserInvoices(SecurityUserDetails user) {
        return invoiceRepository.findAllByUserId(user.getId())
                .collectList()
                .map(it -> ResponseEntity.ok().body(it));
    }
}
