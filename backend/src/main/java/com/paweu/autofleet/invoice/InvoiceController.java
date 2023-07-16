package com.paweu.autofleet.invoice;

import com.paweu.autofleet.data.models.Invoice;
import com.paweu.autofleet.invoice.request.RequestInvoice;
import com.paweu.autofleet.invoice.service.InvoiceService;
import com.paweu.autofleet.security.SecurityUserDetails;
import jakarta.validation.Valid;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/invoice")
@RestController
public class InvoiceController {
    private final InvoiceService invoiceService;

    @PostMapping("/")
    public Mono<ResponseEntity<Invoice>> addInvoice(@RequestBody @Valid RequestInvoice requestNewInvoice,
                                                    @CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails user){
        return invoiceService.addInvoice(requestNewInvoice, user);
    }

    @GetMapping("/")
    public Mono<ResponseEntity<List<Invoice>>> getAllUserInvoices(@CurrentSecurityContext(expression = "authentication.principal") SecurityUserDetails user){
        return invoiceService.getAllUserInvoices(user);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Invoice>> getInvoice(@PathVariable UUID id) {
        return invoiceService.getInvoice(id);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Invoice>> updateInvoice(@PathVariable UUID id){
        return invoiceService.updateInvoice(id);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Invoice>> deleteInvoice(@PathVariable UUID id){
        return invoiceService.deleteInvoice(id);
    }
}
