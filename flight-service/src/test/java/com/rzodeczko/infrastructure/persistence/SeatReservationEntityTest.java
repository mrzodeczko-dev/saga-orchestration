package com.rzodeczko.infrastructure.persistence;

import com.rzodeczko.domain.model.ReservationStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SeatReservationEntityTest {

    @Test
    void builderShouldSetAllFields() {
        UUID id = UUID.randomUUID();
        UUID sagaId = UUID.randomUUID();
        Instant createdAt = Instant.parse("2026-01-01T10:00:00Z");

        SeatReservationEntity entity = SeatReservationEntity.builder()
                .id(id)
                .sagaId(sagaId)
                .customerName("Jan")
                .destination("Mars")
                .status(ReservationStatus.RESERVED)
                .createdAt(createdAt)
                .build();

        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getSagaId()).isEqualTo(sagaId);
        assertThat(entity.getCustomerName()).isEqualTo("Jan");
        assertThat(entity.getDestination()).isEqualTo("Mars");
        assertThat(entity.getStatus()).isEqualTo(ReservationStatus.RESERVED);
        assertThat(entity.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void noArgsConstructorShouldCreateEmptyEntity() {
        SeatReservationEntity entity = new SeatReservationEntity();

        assertThat(entity.getId()).isNull();
        assertThat(entity.getSagaId()).isNull();
    }

    @Test
    void settersShouldUpdateFields() {
        SeatReservationEntity entity = new SeatReservationEntity();

        entity.setCustomerName("Anna");
        entity.setStatus(ReservationStatus.CANCELLED);

        assertThat(entity.getCustomerName()).isEqualTo("Anna");
        assertThat(entity.getStatus()).isEqualTo(ReservationStatus.CANCELLED);
    }
}
