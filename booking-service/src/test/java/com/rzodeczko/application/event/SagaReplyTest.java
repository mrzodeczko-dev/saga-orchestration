package com.rzodeczko.application.event;

import com.rzodeczko.domain.model.saga.SagaStepName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SagaReplyTest {

    @Test
    void shouldExposeAllFieldsForSuccess() {
        UUID sagaId = UUID.randomUUID();
        SagaReply reply = new SagaReply(
                sagaId, SagaStepName.FLIGHT, SagaAction.RESERVE, ReplyStatus.SUCCESS, null);

        assertThat(reply.sagaId()).isEqualTo(sagaId);
        assertThat(reply.step()).isEqualTo(SagaStepName.FLIGHT);
        assertThat(reply.action()).isEqualTo(SagaAction.RESERVE);
        assertThat(reply.status()).isEqualTo(ReplyStatus.SUCCESS);
        assertThat(reply.reason()).isNull();
    }

    @Test
    void shouldExposeAllFieldsForFailure() {
        SagaReply reply = new SagaReply(
                UUID.randomUUID(), SagaStepName.HOTEL, SagaAction.CANCEL, ReplyStatus.FAILURE, "sold out");

        assertThat(reply.status()).isEqualTo(ReplyStatus.FAILURE);
        assertThat(reply.reason()).isEqualTo("sold out");
    }
}
