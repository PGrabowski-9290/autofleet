package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MyEventRepositoryImpl implements MyEventRepository{
    private final DatabaseClient databaseClient;

    @Override
    public Mono<Event> findEventJoinInvoiceJoinInvoicePosByEventId(UUID eventId) {
        final String SQL_QUERY = """
                SELECT e.event_id e_event_id, e.car_id e_car_id, e.event_date e_event_date, e.last_update e_last_update, e.odometer e_odometer,\s
                	e.oil e_oil, e.oil_filter e_oil_filter, e.air_filter e_air_filter, e.timing_belt_kit e_timing_belt_kit, e.description e_description,
                	inv.id inv_id, inv.user_id inv_user_id, inv.invoice_date inv_invoice_date, inv.invoice_number inv_invoice_number, inv.currency inv_currency,\s
                	inv.last_update inv_last_update,
                	invpos.id invpos_id, invpos.invoice_id invpos_invoice_id, invpos.name invpos_name, invpos.price invpos_price, invpos.quantity invpos_quantity
                	FROM public.event e
                	LEFT JOIN public.invoice inv on e.event_id = inv.event_id
                	LEFT JOIN public.invoiceposition invpos on inv.id = invpos.invoice_id
                	WHERE e.event_id = :eventId
                """;

        return databaseClient.sql(SQL_QUERY)
                .bind("eventId", eventId)
                .fetch()
                .all()
                .bufferUntilChanged(res -> res.get("inv_id"))
                .bufferUntilChanged(res -> res.get(0).get("e_event_id"))
                .flatMap(Event::fromRows)
                .singleOrEmpty();
    }
}
