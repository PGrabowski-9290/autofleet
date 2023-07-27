package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;
@Repository
@RequiredArgsConstructor
public class InvoiceRepositoryImpl implements InvoiceRepository{
    private final DatabaseClient databaseClient;
    private final InvoicePosRepository invoicePosRepository;

    private final String SQL_SELECT = """
            SELECT inv.id inv_id, inv.user_id inv_user_id, inv.event_id inv_event_id, inv.invoice_date inv_invoice_date,
            	inv.invoice_number inv_invoice_number, inv.currency inv_currency, inv.last_update inv_last_update,
            	invpos.id invpos_id, invpos.invoice_id invpos_invoice_id, invpos.name invpos_name, invpos.price invpos_price, invpos.quantity invpos_quantity
            	FROM public.invoice inv
            	LEFT JOIN public.invoiceposition invpos on inv.id = invpos.invoice_id
            """;
    @Override
    public Mono<Invoice> findById(UUID id) {
        return databaseClient.sql(String.format("%s WHERE inv.id = :id", SQL_SELECT))
                .bind("id", id)
                .fetch()
                .all()
                .bufferUntilChanged(res -> res.get("inv_id"))
                .flatMap(Invoice::fromRows)
                .singleOrEmpty();
    }

    @Override
    public Flux<Invoice> findAllByUserId(UUID id) {
        return databaseClient.sql(String.format("%s WHERE inv.user_id = :id", SQL_SELECT))
                .bind("id", id)
                .fetch()
                .all()
                .bufferUntilChanged(res -> res.get("inv_id"))
                .flatMap(Invoice::fromRows);
    }

    @Override
    public Mono<Invoice> save(Invoice invoice) {
        return this.saveInvoice(invoice)
                .flatMap(this::saveInvoicePos);
    }

    @Override
    public Mono<Long> deleteById(UUID id) {
        return databaseClient.sql("DELETE FROM public.invoice WHERE id = :id")
                .bind("id", id)
                .fetch()
                .rowsUpdated()
                .flatMap(res -> {
                    if (res != 0)
                        return Mono.just(res);
                    else
                        return Mono.empty();
                });
    }

    private Mono<Invoice> saveInvoice(Invoice invoice){
        if (invoice.getId() == null){
            return databaseClient.sql("INSERT INTO public.invoice(user_id, event_id, invoice_date, invoice_number, currency)" +
                            "VALUES (:userId, :eventId, :invoiceDate, :invoiceNumber, :currency)")
                    .filter(statement -> statement.returnGeneratedValues("id", "last_update"))
                    .bind("userId", invoice.getUserId())
                    .bind("eventId", invoice.getEventId())
                    .bind("invoiceDate", invoice.getDate())
                    .bind("invoiceNumber", invoice.getInvoiceNumber())
                    .bind("currency", invoice.getCurrency())
                    .fetch()
                    .first()
                    .doOnNext(res -> {
                        invoice.setId((UUID) res.get("id"));
                        invoice.setLastUpdate((LocalDateTime) res.get("last_update"));
                    })
                    .thenReturn(invoice);
        } else {
            return databaseClient.sql("UPDATE public.invoice SET invoice_number = :invoiceNumber, invoice_date = :invoiceDate, "
                            + "currency = :currency, last_update = :lastUpdate WHERE id= :invoiceId")
                    .bind("invoiceNumber", invoice.getInvoiceNumber())
                    .bind("invoiceDate", invoice.getDate())
                    .bind("currency", invoice.getCurrency())
                    .bind("lastUpdate", invoice.getLastUpdate())
                    .bind("invoiceId", invoice.getId())
                    .fetch().first()
                    .thenReturn(invoice);
        }
    }

    private Mono<Invoice> saveInvoicePos(Invoice invoice){
        return Flux.fromIterable(invoice.getInvoicePosList()
                        .stream()
                        .peek(it -> it.setInvoiceId(invoice.getId()))
                        .toList())
                .flatMap(invoicePosRepository::save)
                .collectList()
                .doOnNext(invoice::setInvoicePosList)
                .thenReturn(invoice);
    }
}
