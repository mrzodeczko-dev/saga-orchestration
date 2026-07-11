package com.rzodeczko.infrastructure.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SagaTopologyProperties.class)
public class SagaTopologyConfig {
    private final SagaTopologyProperties topology;

    @Bean
    public Declarables sagaTopology() {
        DirectExchange commandsExchange = new DirectExchange(topology.commandsExchange(), true, false);
        DirectExchange repliesExchange = new DirectExchange(topology.repliesExchange(), true, false);
        DirectExchange dlxExchange = new DirectExchange(topology.dlxExchange(), true, false);

        Queue replyQueue = QueueBuilder
                .durable(topology.replyQueue())
                .deadLetterExchange(topology.dlxExchange())
                .deadLetterRoutingKey(topology.replyDlqRoutingKey())
                .build();

        Binding replyBinding = BindingBuilder
                .bind(replyQueue)
                .to(repliesExchange)
                .with(topology.replyRoutingKey());

        Queue replyDlq = QueueBuilder
                .durable(topology.replyDlq())
                .build();

        Binding replyDlqBinding = BindingBuilder
                .bind(replyDlq)
                .to(dlxExchange)
                .with(topology.replyDlqRoutingKey());

        return new Declarables(
                commandsExchange,
                repliesExchange,
                dlxExchange,
                replyQueue,
                replyBinding,
                replyDlq,
                replyDlqBinding
        );
    }
}
