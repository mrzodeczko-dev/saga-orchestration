package com.rzodeczko.presentation.dto.response;

import com.rzodeczko.application.dto.SagaInstanceDto;
import com.rzodeczko.application.dto.SagaStepDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookingResponseDtoTest {

    @Test
    void fromShouldMapAllFieldsIncludingSteps() {
        Instant now = Instant.now();
        SagaInstanceDto app = new SagaInstanceDto(
                "saga-1", "Jan", "Mars", new BigDecimal("100"), "IN_PROGRESS",
                List.of(new SagaStepDto("FLIGHT", "PENDING", null)),
                now, now
        );

        BookingResponseDto response = BookingResponseDto.from(app);

        assertThat(response.sagaId()).isEqualTo("saga-1");
        assertThat(response.customerName()).isEqualTo("Jan");
        assertThat(response.destination()).isEqualTo("Mars");
        assertThat(response.amount()).isEqualByComparingTo("100");
        assertThat(response.status()).isEqualTo("IN_PROGRESS");
        assertThat(response.steps()).hasSize(1);
        assertThat(response.steps().getFirst().name()).isEqualTo("FLIGHT");
        assertThat(response.createdAt()).isEqualTo(now);
        assertThat(response.updatedAt()).isEqualTo(now);
    }

    @Test
    void fromShouldHandleEmptyStepList() {
        SagaInstanceDto app = new SagaInstanceDto(
                "s", "X", "Y", BigDecimal.TEN, "COMPLETED",
                List.of(), Instant.now(), Instant.now());

        assertThat(BookingResponseDto.from(app).steps()).isEmpty();
    }
}
