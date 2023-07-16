package com.paweu.autofleet.invoice;

import com.paweu.autofleet.data.models.Invoice;
import com.paweu.autofleet.invoice.request.RequestInvoice;
import com.paweu.autofleet.invoice.service.InvoiceService;
import com.paweu.autofleet.security.SecurityUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

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
}
