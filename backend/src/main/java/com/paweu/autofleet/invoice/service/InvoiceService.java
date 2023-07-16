package com.paweu.autofleet.invoice.service;

import com.paweu.autofleet.data.models.Invoice;
import com.paweu.autofleet.data.models.InvoicePos;
import com.paweu.autofleet.data.repository.InvoiceRepository;
import com.paweu.autofleet.invoice.request.RequestInvoice;
import com.paweu.autofleet.security.SecurityUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

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
}
