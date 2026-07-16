package com.rzodeczko.infrastructure.messaging.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SagaReplyMessageTest {

    @Test
    void shouldExposeAllFieldsViaAccessors() {
        UUID sagaId = UUID.randomUUID();

        SagaReplyMessage reply = new SagaReplyMessage(
                sagaId, "HOTEL", "RESERVE", "SUCCESS", null);

        assertThat(reply.sagaId()).isEqualTo(sagaId);
        assertThat(reply.step()).isEqualTo("HOTEL");
        assertThat(reply.action()).isEqualTo("RESERVE");
        assertThat(reply.status()).isEqualTo("SUCCESS");
        assertThat(reply.reason()).isNull();
    }

    @Test
    void shouldPreserveFailureReason() {
        SagaReplyMessage reply = new SagaReplyMessage(
                UUID.randomUUID(), "HOTEL", "RESERVE", "FAILURE", "no cabins");

        assertThat(reply.status()).isEqualTo("FAILURE");
        assertThat(reply.reason()).isEqualTo("no cabins");
    }
}
