package com.rzodeczko.domain.model.saga;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SagaStatusTest {

    @Test
    void shouldContainAllExpectedStates() {
        assertThat(SagaStatus.values()).containsExactly(
                SagaStatus.IN_PROGRESS,
                SagaStatus.COMPLETED,
                SagaStatus.COMPENSATING,
                SagaStatus.CANCELLED,
                SagaStatus.COMPENSATION_FAILED
        );
    }

    @Test
    void valueOfShouldMatchEnumName() {
        assertThat(SagaStatus.valueOf("IN_PROGRESS")).isEqualTo(SagaStatus.IN_PROGRESS);
        assertThat(SagaStatus.valueOf("COMPENSATING")).isEqualTo(SagaStatus.COMPENSATING);
        assertThat(SagaStatus.valueOf("COMPENSATION_FAILED")).isEqualTo(SagaStatus.COMPENSATION_FAILED);
    }
}
