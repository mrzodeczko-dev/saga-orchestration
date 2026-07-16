package com.rzodeczko.domain.model.saga;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SagaStepStatusTest {

    @Test
    void shouldContainAllExpectedStates() {
        assertThat(SagaStepStatus.values()).containsExactly(
                SagaStepStatus.PENDING,
                SagaStepStatus.RESERVED,
                SagaStepStatus.FAILED,
                SagaStepStatus.COMPENSATED,
                SagaStepStatus.COMPENSATION_FAILED
        );
    }

    @Test
    void valueOfShouldMatchEnumName() {
        assertThat(SagaStepStatus.valueOf("PENDING")).isEqualTo(SagaStepStatus.PENDING);
        assertThat(SagaStepStatus.valueOf("RESERVED")).isEqualTo(SagaStepStatus.RESERVED);
        assertThat(SagaStepStatus.valueOf("FAILED")).isEqualTo(SagaStepStatus.FAILED);
        assertThat(SagaStepStatus.valueOf("COMPENSATED")).isEqualTo(SagaStepStatus.COMPENSATED);
        assertThat(SagaStepStatus.valueOf("COMPENSATION_FAILED")).isEqualTo(SagaStepStatus.COMPENSATION_FAILED);
    }
}
