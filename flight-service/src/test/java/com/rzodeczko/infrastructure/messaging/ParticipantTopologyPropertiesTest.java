package com.rzodeczko.infrastructure.messaging;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipantTopologyPropertiesTest {

    @Test
    void shouldExposeAllTopologyPropertiesViaAccessors() {
        ParticipantTopologyProperties props = new ParticipantTopologyProperties(
                "x.saga.commands", "x.saga.replies", "x.saga.dlx",
                "q.flight-service.commands", "q.flight-service.commands.dlq",
                "flight.command", "flight.dlq", "saga.reply");

        assertThat(props.commandsExchange()).isEqualTo("x.saga.commands");
        assertThat(props.repliesExchange()).isEqualTo("x.saga.replies");
        assertThat(props.dlxExchange()).isEqualTo("x.saga.dlx");
        assertThat(props.commandQueue()).isEqualTo("q.flight-service.commands");
        assertThat(props.commandDlq()).isEqualTo("q.flight-service.commands.dlq");
        assertThat(props.commandRoutingKey()).isEqualTo("flight.command");
        assertThat(props.commandDlqRoutingKey()).isEqualTo("flight.dlq");
        assertThat(props.replyRoutingKey()).isEqualTo("saga.reply");
    }
}
