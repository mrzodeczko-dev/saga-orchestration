package com.rzodeczko.application.event;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SagaParticipantReplyTest {

    @Test
    void fromShouldBuildSuccessReplyFromSuccessResult() {
        UUID sagaId = UUID.randomUUID();
        SagaParticipantReply reply = SagaParticipantReply.from(
                sagaId, "FLIGHT", SagaAction.RESERVE, CommandResult.success());

        assertThat(reply.sagaId()).isEqualTo(sagaId);
        assertThat(reply.step()).isEqualTo("FLIGHT");
        assertThat(reply.action()).isEqualTo(SagaAction.RESERVE);
        assertThat(reply.status()).isEqualTo("SUCCESS");
        assertThat(reply.reason()).isNull();
    }

    @Test
    void fromShouldBuildFailureReplyFromFailureResult() {
        UUID sagaId = UUID.randomUUID();
        SagaParticipantReply reply = SagaParticipantReply.from(
                sagaId, "FLIGHT", SagaAction.CANCEL, CommandResult.failure("no seats"));

        assertThat(reply.sagaId()).isEqualTo(sagaId);
        assertThat(reply.step()).isEqualTo("FLIGHT");
        assertThat(reply.action()).isEqualTo(SagaAction.CANCEL);
        assertThat(reply.status()).isEqualTo("FAILURE");
        assertThat(reply.reason()).isEqualTo("no seats");
    }
}
