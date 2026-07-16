package com.rzodeczko.infrastructure.messaging;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipantTopologyPropertiesTest {

    @Test
    void shouldExposeAllTopologyPropertiesViaAccessors() {
        ParticipantTopologyProperties props = new ParticipantTopologyProperties(
                "x.saga.commands", "x.saga.replies", "x.saga.dlx",
                "q.payment-service.commands", "q.payment-service.commands.dlq",
                "payment.command", "payment.dlq", "saga.reply");

        assertThat(props.commandsExchange()).isEqualTo("x.saga.commands");
        assertThat(props.repliesExchange()).isEqualTo("x.saga.replies");
        assertThat(props.dlxExchange()).isEqualTo("x.saga.dlx");
        assertThat(props.commandQueue()).isEqualTo("q.payment-service.commands");
        assertThat(props.commandDlq()).isEqualTo("q.payment-service.commands.dlq");
        assertThat(props.commandRoutingKey()).isEqualTo("payment.command");
        assertThat(props.commandDlqRoutingKey()).isEqualTo("payment.dlq");
        assertThat(props.replyRoutingKey()).isEqualTo("saga.reply");
    }
}
