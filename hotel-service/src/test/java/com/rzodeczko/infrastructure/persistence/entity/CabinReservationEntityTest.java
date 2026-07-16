package com.rzodeczko.infrastructure.persistence.entity;

import com.rzodeczko.domain.model.ReservationStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CabinReservationEntityTest {

    @Test
    void builderShouldSetAllFields() {
        UUID id = UUID.randomUUID();
        UUID sagaId = UUID.randomUUID();
        Instant createdAt = Instant.parse("2026-01-01T10:00:00Z");

        CabinReservationEntity entity = CabinReservationEntity.builder()
                .id(id)
                .sagaId(sagaId)
                .customerName("Anna")
                .destination("Venus")
                .status(ReservationStatus.RESERVED)
                .createdAt(createdAt)
                .build();

        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getSagaId()).isEqualTo(sagaId);
        assertThat(entity.getCustomerName()).isEqualTo("Anna");
        assertThat(entity.getDestination()).isEqualTo("Venus");
        assertThat(entity.getStatus()).isEqualTo(ReservationStatus.RESERVED);
        assertThat(entity.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void settersShouldUpdateFields() {
        CabinReservationEntity entity = new CabinReservationEntity();

        entity.setDestination("Titan");
        entity.setStatus(ReservationStatus.CANCELLED);

        assertThat(entity.getDestination()).isEqualTo("Titan");
        assertThat(entity.getStatus()).isEqualTo(ReservationStatus.CANCELLED);
    }
}
