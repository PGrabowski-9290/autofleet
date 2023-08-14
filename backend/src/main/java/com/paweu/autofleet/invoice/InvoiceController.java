package com.paweu.autofleet.invoice;

import com.paweu.autofleet.data.models.Invoice;
import com.paweu.autofleet.invoice.request.RequestInvoice;
import com.paweu.autofleet.invoice.request.RequestInvoiceUpdate;
import com.paweu.autofleet.invoice.response.ResponseDeleted;
import com.paweu.autofleet.security.SecurityUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearer-key")
public interface InvoiceController {
    @Operation(
            description = "Add invoice to existing event",
            summary = "Add invoice"
    )
    @PostMapping("/")
    Mono<ResponseEntity<Invoice>> addInvoice(@RequestBody @Valid RequestInvoice requestNewInvoice,
                                             @AuthenticationPrincipal SecurityUserDetails user);

    @Operation(
            description = "Get list of all user invoices from all events",
            summary = "Get all invoices"
    )
    @GetMapping("/")
    Mono<ResponseEntity<List<Invoice>>> getAllUserInvoices(@AuthenticationPrincipal SecurityUserDetails user);

    @Operation(
            description = "Get invoice details by id",
            summary = "Get invoice"
    )
    @GetMapping("/{id}")
    Mono<ResponseEntity<Invoice>> getInvoice(@PathVariable UUID id);

    @Operation(
            description = "Update invoice and invoice positions",
            summary = "Update invoice"
    )
    @PutMapping("/{id}")
    Mono<ResponseEntity<Invoice>> updateInvoice(@PathVariable UUID id, @RequestBody RequestInvoiceUpdate invoiceUpdate);

    @Operation(
            description = "Delete invoice and return amount of deleted records",
            summary = "Delete invoice"
    )
    @DeleteMapping("/{id}")
    Mono<ResponseEntity<ResponseDeleted>> deleteInvoice(@PathVariable UUID id);
}
