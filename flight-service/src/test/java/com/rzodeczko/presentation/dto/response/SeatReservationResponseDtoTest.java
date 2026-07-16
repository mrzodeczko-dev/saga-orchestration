package com.rzodeczko.presentation.dto.response;

import com.rzodeczko.application.dto.SeatReservationDto;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class SeatReservationResponseDtoTest {

    @Test
    void fromShouldCopyAllFieldsFromApplicationDto() {
        Instant createdAt = Instant.parse("2026-01-01T12:00:00Z");
        SeatReservationDto dto = new SeatReservationDto(
                "id-1", "saga-1", "Jan", "Mars", "RESERVED", createdAt);

        SeatReservationResponseDto response = SeatReservationResponseDto.from(dto);

        assertThat(response.id()).isEqualTo("id-1");
        assertThat(response.sagaId()).isEqualTo("saga-1");
        assertThat(response.customerName()).isEqualTo("Jan");
        assertThat(response.destination()).isEqualTo("Mars");
        assertThat(response.status()).isEqualTo("RESERVED");
        assertThat(response.createdAt()).isEqualTo(createdAt);
    }
}
