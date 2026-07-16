package com.rzodeczko.application.dto;

import com.rzodeczko.domain.model.saga.SagaStep;
import com.rzodeczko.domain.model.saga.SagaStepName;
import com.rzodeczko.domain.model.saga.SagaStepStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SagaStepDtoTest {

    @Test
    void fromShouldMapAllFields() {
        SagaStep step = SagaStep.restore(SagaStepName.FLIGHT, SagaStepStatus.RESERVED, null);

        SagaStepDto dto = SagaStepDto.from(step);

        assertThat(dto.name()).isEqualTo("FLIGHT");
        assertThat(dto.status()).isEqualTo("RESERVED");
        assertThat(dto.reason()).isNull();
    }

    @Test
    void fromShouldPreserveFailureReason() {
        SagaStep step = SagaStep.restore(SagaStepName.PAYMENT, SagaStepStatus.FAILED, "declined");

        SagaStepDto dto = SagaStepDto.from(step);

        assertThat(dto.name()).isEqualTo("PAYMENT");
        assertThat(dto.status()).isEqualTo("FAILED");
        assertThat(dto.reason()).isEqualTo("declined");
    }
}
