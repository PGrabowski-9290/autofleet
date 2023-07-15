package com.paweu.autofleet.data.repository;

import com.paweu.autofleet.data.models.Event;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends R2dbcRepository<Event, UUID>, MyEventRepository {
}
