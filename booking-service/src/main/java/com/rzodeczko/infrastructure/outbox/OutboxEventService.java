package com.rzodeczko.infrastructure.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OutboxEventService {
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public OutboxEvent save(String eventType, Object payload, String exchange, String routingKey) {
        try {
            String json = objectMapper.writeValueAsString(payload);
            return outboxEventRepository.save(OutboxEvent
                    .builder()
                    .eventType(eventType)
                    .payload(json)
                    .exchange(exchange)
                    .routingKey(routingKey)
                    .createdAt(LocalDateTime.now())
                    .published(false)
                    .build());
        } catch (JsonProcessingException e) {
            throw new OutboxSerializationException("Cannot serialize: " + eventType, e);
        }
    }
}
