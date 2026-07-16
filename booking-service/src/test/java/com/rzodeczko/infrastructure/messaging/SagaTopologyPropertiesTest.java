package com.rzodeczko.infrastructure.messaging;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SagaTopologyPropertiesTest {

    @Test
    void shouldExposeAllTopologyPropertiesViaAccessors() {
        SagaTopologyProperties props = new SagaTopologyProperties(
                "x.saga.commands", "x.saga.replies", "x.saga.dlx",
                "q.booking-service.replies", "q.booking-service.replies.dlq",
                "saga.reply", "reply.dlq",
                "flight.command", "hotel.command", "payment.command");

        assertThat(props.commandsExchange()).isEqualTo("x.saga.commands");
        assertThat(props.repliesExchange()).isEqualTo("x.saga.replies");
        assertThat(props.dlxExchange()).isEqualTo("x.saga.dlx");
        assertThat(props.replyQueue()).isEqualTo("q.booking-service.replies");
        assertThat(props.replyDlq()).isEqualTo("q.booking-service.replies.dlq");
        assertThat(props.replyRoutingKey()).isEqualTo("saga.reply");
        assertThat(props.replyDlqRoutingKey()).isEqualTo("reply.dlq");
        assertThat(props.flightCommandRoutingKey()).isEqualTo("flight.command");
        assertThat(props.hotelCommandRoutingKey()).isEqualTo("hotel.command");
        assertThat(props.paymentCommandRoutingKey()).isEqualTo("payment.command");
    }
}
