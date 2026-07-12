package com.rzodeczko.contracts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzodeczko.infrastructure.messaging.dto.SagaReplyMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.converter.YamlContract;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierReceiver;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifierSender;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessage;
import org.springframework.cloud.contract.verifier.messaging.internal.ContractVerifierMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import jakarta.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@SpringBootTest(
        classes = MessagingContractBaseTest.TestConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@AutoConfigureMessageVerifier
public abstract class MessagingContractBaseTest {

    @Autowired
    private ContractVerifierMessaging contractVerifierMessaging;

    @Autowired
    private ObjectMapper objectMapper;

    private static final UUID SAGA_ID = UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    public void hotel_reserve_success() throws Exception {
        SagaReplyMessage reply = new SagaReplyMessage(
                SAGA_ID, "HOTEL", "RESERVE", "SUCCESS", null
        );
        sendMessage(reply, "x.saga.replies");
    }

    public void hotel_reserve_failure() throws Exception {
        SagaReplyMessage reply = new SagaReplyMessage(
                SAGA_ID, "HOTEL", "RESERVE", "FAILURE",
                "No cabins available at destination"
        );
        sendMessage(reply, "x.saga.replies");
    }

    public void hotel_cancel_success() throws Exception {
        SagaReplyMessage reply = new SagaReplyMessage(
                SAGA_ID, "HOTEL", "CANCEL", "SUCCESS", null
        );
        sendMessage(reply, "x.saga.replies");
    }

    private void sendMessage(Object payload, String destination) throws Exception {
        String json = objectMapper.writeValueAsString(payload);
        Map<String, Object> headers = new HashMap<>();
        headers.put("contentType", "application/json");
        ContractVerifierMessage message = new ContractVerifierMessage(json, headers);
        contractVerifierMessaging.send(message, destination);
    }

    @Configuration
    static class TestConfig {

        private final Map<String, Message<?>> sent = new ConcurrentHashMap<>();

        @Bean
        public ObjectMapper objectMapper() {
            return new ObjectMapper();
        }

        @Bean
        public MessageVerifierSender<Message<?>> messageVerifierSender() {
            return new MessageVerifierSender<>() {
                @Override
                public <T> void send(T payload, Map<String, Object> headers, String destination, @Nullable YamlContract contract) {
                    MessageBuilder<T> builder = MessageBuilder.withPayload(payload);
                    headers.forEach(builder::setHeader);
                    sent.put(destination, builder.build());
                }

                @Override
                public void send(Message<?> message, String destination, @Nullable YamlContract contract) {
                    sent.put(destination, message);
                }
            };
        }

        @Bean
        public MessageVerifierReceiver<Message<?>> messageVerifierReceiver() {
            return new MessageVerifierReceiver<>() {
                @Override
                public Message<?> receive(String destination, long timeout, TimeUnit timeUnit, @Nullable YamlContract contract) {
                    return sent.remove(destination);
                }

                @Override
                public Message<?> receive(String destination, YamlContract contract) {
                    return sent.remove(destination);
                }
            };
        }
    }
}
