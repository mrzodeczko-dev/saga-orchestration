package com.rzodeczko.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxEventPublisher {
    private final OutboxEventRepository outboxEventRepository;
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelayString = "${app.rabbitmq.outbox.poll-interval-ms:1000}")
    @SchedulerLock(name = "booking_outbox_publisher", lockAtMostFor = "30s", lockAtLeastFor = "500ms")
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEvent> unpublished = outboxEventRepository.findTop100ByPublishedFalseOrderByCreatedAtAsc();
        if (unpublished.isEmpty()) {
            return;
        }

        log.debug("[OUTBOX] Found {} unpublished event(s)", unpublished.size());
        for (OutboxEvent event : unpublished) {
            publishSingle(event);
        }
    }

    private void publishSingle(OutboxEvent event) {
        try {
            Message message = MessageBuilder
                    .withBody(event.getPayload().getBytes(StandardCharsets.UTF_8))
                    .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                    .build();

            // Synchroniczna wysylka. Wraz z publisher-confirms i template.retry daje
            // mocna gwarancje, ze broker przyjal wiadomosc zanim oznaczymy published=true
            rabbitTemplate.send(event.getExchange(), event.getRoutingKey(), message);

            event.publishSuccess();
            outboxEventRepository.save(event);
            log.info(
                    "[OUTBOX] Published type={}, id={}, attempts={}",
                    event.getEventType(),
                    event.getId(),
                    event.getAttemptCount()
            );
        } catch (Exception e) {
            event.publishFailure(e.getMessage());
            outboxEventRepository.save(event);
            log.error(
                    "[OUTBOX] Publish failed type={}, id={}, attempt={}, error={}",
                    event.getEventType(),
                    event.getId(),
                    event.getAttemptCount(),
                    e.getMessage()
            );
        }
    }
}
