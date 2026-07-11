package com.rzodeczko.infrastructure.messaging;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.rabbitmq.topology")
public record SagaTopologyProperties(
        String commandsExchange,
        String repliesExchange,
        String dlxExchange,
        String replyQueue,
        String replyDlq,
        String replyRoutingKey,
        String replyDlqRoutingKey,
        String flightCommandRoutingKey,
        String hotelCommandRoutingKey,
        String paymentCommandRoutingKey
) { }
