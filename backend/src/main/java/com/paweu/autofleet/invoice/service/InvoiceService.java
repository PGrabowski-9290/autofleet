package com.paweu.autofleet.invoice.service;

import com.paweu.autofleet.data.models.Invoice;
import com.paweu.autofleet.data.models.InvoicePos;
import com.paweu.autofleet.data.repository.InvoiceRepository;
import com.paweu.autofleet.invoice.request.RequestInvoice;
import com.paweu.autofleet.invoice.response.ResponseDeleted;
import com.paweu.autofleet.security.SecurityUserDetails;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

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

    @SneakyThrows
    public Mono<ResponseEntity<Invoice>> updateInvoice(UUID id) {
        throw new ExecutionControl.NotImplementedException("update invoice not implemented");

    }

    @SneakyThrows
    public Mono<ResponseEntity<List<Invoice>>> getAllUserInvoices(SecurityUserDetails user) {
        throw new ExecutionControl.NotImplementedException("Get all user invoices not implemented");
    }
}
