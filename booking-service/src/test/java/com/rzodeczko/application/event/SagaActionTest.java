package com.rzodeczko.application.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SagaActionTest {

    @Test
    void shouldContainReserveAndCancel() {
        assertThat(SagaAction.values()).containsExactly(SagaAction.RESERVE, SagaAction.CANCEL);
    }

    @Test
    void valueOfShouldMatchEnumName() {
        assertThat(SagaAction.valueOf("RESERVE")).isEqualTo(SagaAction.RESERVE);
        assertThat(SagaAction.valueOf("CANCEL")).isEqualTo(SagaAction.CANCEL);
    }
}
