package com.rzodeczko.domain.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SagaExceptionsTest {

    @Test
    void sagaNotFoundShouldIncludeSagaIdInMessage() {
        UUID sagaId = UUID.randomUUID();

        SagaNotFoundException ex = new SagaNotFoundException(sagaId);

        assertThat(ex.getMessage()).contains(sagaId.toString());
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }

    @Test
    void invalidSagaStateShouldExposeMessage() {
        InvalidSagaStateException ex = new InvalidSagaStateException("bad state");

        assertThat(ex.getMessage()).isEqualTo("bad state");
        assertThat(ex).isInstanceOf(RuntimeException.class);
    }
}
