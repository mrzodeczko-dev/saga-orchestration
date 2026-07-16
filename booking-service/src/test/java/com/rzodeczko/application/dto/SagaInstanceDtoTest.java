package com.rzodeczko.application.dto;

import com.rzodeczko.domain.model.saga.SagaInstance;
import com.rzodeczko.domain.model.saga.SagaStatus;
import com.rzodeczko.domain.model.saga.SagaStep;
import com.rzodeczko.domain.model.saga.SagaStepName;
import com.rzodeczko.domain.model.saga.SagaStepStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SagaInstanceDtoTest {

    @Test
    void fromShouldMapAllFieldsIncludingSteps() {
        UUID id = UUID.randomUUID();
        Instant createdAt = Instant.parse("2026-01-01T10:00:00Z");
        Instant updatedAt = Instant.parse("2026-01-01T10:05:00Z");
        SagaInstance saga = SagaInstance.restore(
                id, "Jan", "Mars", new BigDecimal("2500.00"),
                SagaStatus.COMPLETED,
                List.of(
                        SagaStep.restore(SagaStepName.FLIGHT, SagaStepStatus.RESERVED, null),
                        SagaStep.restore(SagaStepName.HOTEL, SagaStepStatus.RESERVED, null),
                        SagaStep.restore(SagaStepName.PAYMENT, SagaStepStatus.RESERVED, null)
                ),
                createdAt, updatedAt
        );

        SagaInstanceDto dto = SagaInstanceDto.from(saga);

        assertThat(dto.sagaId()).isEqualTo(id.toString());
        assertThat(dto.customerName()).isEqualTo("Jan");
        assertThat(dto.destination()).isEqualTo("Mars");
        assertThat(dto.amount()).isEqualByComparingTo("2500.00");
        assertThat(dto.status()).isEqualTo("COMPLETED");
        assertThat(dto.steps()).hasSize(3)
                .extracting(SagaStepDto::name)
                .containsExactly("FLIGHT", "HOTEL", "PAYMENT");
        assertThat(dto.createdAt()).isEqualTo(createdAt);
        assertThat(dto.updatedAt()).isEqualTo(updatedAt);
    }

    @Test
    void fromShouldHandleInProgressSagaWithPendingSteps() {
        SagaInstance saga = SagaInstance.start("Anna", "Venus", BigDecimal.TEN);

        SagaInstanceDto dto = SagaInstanceDto.from(saga);

        assertThat(dto.status()).isEqualTo("IN_PROGRESS");
        assertThat(dto.steps()).allSatisfy(step -> assertThat(step.status()).isEqualTo("PENDING"));
    }
}
