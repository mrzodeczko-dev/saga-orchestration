package com.rzodeczko.infrastructure.messaging;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipantTopologyPropertiesTest {

    @Test
    void shouldExposeAllTopologyPropertiesViaAccessors() {
        ParticipantTopologyProperties props = new ParticipantTopologyProperties(
                "x.saga.commands", "x.saga.replies", "x.saga.dlx",
                "q.hotel-service.commands", "q.hotel-service.commands.dlq",
                "hotel.command", "hotel.dlq", "saga.reply");

        assertThat(props.commandsExchange()).isEqualTo("x.saga.commands");
        assertThat(props.repliesExchange()).isEqualTo("x.saga.replies");
        assertThat(props.dlxExchange()).isEqualTo("x.saga.dlx");
        assertThat(props.commandQueue()).isEqualTo("q.hotel-service.commands");
        assertThat(props.commandDlq()).isEqualTo("q.hotel-service.commands.dlq");
        assertThat(props.commandRoutingKey()).isEqualTo("hotel.command");
        assertThat(props.commandDlqRoutingKey()).isEqualTo("hotel.dlq");
        assertThat(props.replyRoutingKey()).isEqualTo("saga.reply");
    }
}
