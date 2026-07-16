package com.rzodeczko.infrastructure.messaging.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SagaReplyMessageDtoTest {

    @Test
    void shouldExposeAllFieldsForSuccess() {
        UUID sagaId = UUID.randomUUID();
        SagaReplyMessageDto reply = new SagaReplyMessageDto(
                sagaId, "FLIGHT", "RESERVE", "SUCCESS", null);

        assertThat(reply.sagaId()).isEqualTo(sagaId);
        assertThat(reply.step()).isEqualTo("FLIGHT");
        assertThat(reply.action()).isEqualTo("RESERVE");
        assertThat(reply.status()).isEqualTo("SUCCESS");
        assertThat(reply.reason()).isNull();
    }

    @Test
    void shouldPreserveFailureReason() {
        SagaReplyMessageDto reply = new SagaReplyMessageDto(
                UUID.randomUUID(), "FLIGHT", "RESERVE", "FAILURE", "no seats");

        assertThat(reply.status()).isEqualTo("FAILURE");
        assertThat(reply.reason()).isEqualTo("no seats");
    }
}
