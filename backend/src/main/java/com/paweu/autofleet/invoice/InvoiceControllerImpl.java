package com.paweu.autofleet.invoice;

import com.paweu.autofleet.data.models.Invoice;
import com.paweu.autofleet.invoice.request.RequestInvoice;
import com.paweu.autofleet.invoice.response.ResponseDeleted;
import com.paweu.autofleet.invoice.service.InvoiceService;
import com.paweu.autofleet.security.SecurityUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/invoice")
@RestController
public class InvoiceControllerImpl implements InvoiceController {
    private final InvoiceService invoiceService;

    @Override
    public Mono<ResponseEntity<Invoice>> addInvoice(@Valid RequestInvoice requestNewInvoice,
                                                    SecurityUserDetails user) {
        return invoiceService.addInvoice(requestNewInvoice, user);
    }

    @Override
    public Mono<ResponseEntity<List<Invoice>>> getAllUserInvoices(SecurityUserDetails user) {
        return invoiceService.getAllUserInvoices(user);
    }

    @Override
    public Mono<ResponseEntity<Invoice>> getInvoice(UUID id) {
        return invoiceService.getInvoice(id);
    }

    @Override
    public Mono<ResponseEntity<Invoice>> updateInvoice(UUID id) {
        return invoiceService.updateInvoice(id);
    }

    @Override
    public Mono<ResponseEntity<ResponseDeleted>> deleteInvoice(UUID id) {
        return invoiceService.deleteInvoice(id);
    }
}
