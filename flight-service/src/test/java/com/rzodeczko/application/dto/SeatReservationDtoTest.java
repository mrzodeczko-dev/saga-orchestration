package com.rzodeczko.application.dto;

import com.rzodeczko.domain.model.ReservationStatus;
import com.rzodeczko.domain.model.SeatReservation;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SeatReservationDtoTest {

    @Test
    void fromShouldMapAllFieldsFromDomainModel() {
        UUID id = UUID.randomUUID();
        UUID sagaId = UUID.randomUUID();
        Instant createdAt = Instant.parse("2026-01-01T10:00:00Z");
        SeatReservation reservation = SeatReservation.restore(
                id, sagaId, "Jan", "Mars", ReservationStatus.RESERVED, createdAt);

        SeatReservationDto dto = SeatReservationDto.from(reservation);

        assertThat(dto.id()).isEqualTo(id.toString());
        assertThat(dto.sagaId()).isEqualTo(sagaId.toString());
        assertThat(dto.customerName()).isEqualTo("Jan");
        assertThat(dto.destination()).isEqualTo("Mars");
        assertThat(dto.status()).isEqualTo("RESERVED");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
    }

    @Test
    void fromShouldMapCancelledStatus() {
        SeatReservation reservation = SeatReservation.restore(
                UUID.randomUUID(), UUID.randomUUID(), "Anna", "Venus",
                ReservationStatus.CANCELLED, Instant.now());

        SeatReservationDto dto = SeatReservationDto.from(reservation);

        assertThat(dto.status()).isEqualTo("CANCELLED");
    }
}
