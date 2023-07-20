package com.paweu.autofleet.invoice;

import com.paweu.autofleet.data.models.Invoice;
import com.paweu.autofleet.invoice.request.RequestInvoice;
import com.paweu.autofleet.security.SecurityUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RequestMapping("/invoice")
@RestController
@Tag(name = "Invoice")
public interface InvoiceController {
    @PostMapping("/")
    Mono<ResponseEntity<Invoice>> addInvoice(@RequestBody @Valid RequestInvoice requestNewInvoice,
                                             @AuthenticationPrincipal SecurityUserDetails user);

    @GetMapping("/")
    Mono<ResponseEntity<List<Invoice>>> getAllUserInvoices(@AuthenticationPrincipal SecurityUserDetails user);

    @GetMapping("/{id}")
    Mono<ResponseEntity<Invoice>> getInvoice(@PathVariable UUID id);

    @PutMapping("/{id}")
    Mono<ResponseEntity<Invoice>> updateInvoice(@PathVariable UUID id);

    @DeleteMapping("/{id}")
    Mono<ResponseEntity<Invoice>> deleteInvoice(@PathVariable UUID id);
}
