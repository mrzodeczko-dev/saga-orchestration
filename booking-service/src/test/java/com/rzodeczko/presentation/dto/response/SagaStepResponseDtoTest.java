package com.rzodeczko.presentation.dto.response;

import com.rzodeczko.application.dto.SagaStepDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SagaStepResponseDtoTest {

    @Test
    void fromShouldCopyAllFields() {
        SagaStepDto app = new SagaStepDto("HOTEL", "RESERVED", null);

        SagaStepResponseDto response = SagaStepResponseDto.from(app);

        assertThat(response.name()).isEqualTo("HOTEL");
        assertThat(response.status()).isEqualTo("RESERVED");
        assertThat(response.reason()).isNull();
    }

    @Test
    void fromShouldPreserveReason() {
        SagaStepDto app = new SagaStepDto("PAYMENT", "FAILED", "insufficient funds");

        SagaStepResponseDto response = SagaStepResponseDto.from(app);

        assertThat(response.reason()).isEqualTo("insufficient funds");
    }
}
