package com.rzodeczko.infrastructure.messaging.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PaymentCommandMessageTest {

    @Test
    void shouldExposeAllFieldsViaAccessors() {
        UUID sagaId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("1499.99");

        PaymentCommandMessage msg = new PaymentCommandMessage(
                sagaId, "RESERVE", "Jan", "Mars", amount);

        assertThat(msg.sagaId()).isEqualTo(sagaId);
        assertThat(msg.action()).isEqualTo("RESERVE");
        assertThat(msg.customerName()).isEqualTo("Jan");
        assertThat(msg.destination()).isEqualTo("Mars");
        assertThat(msg.amount()).isEqualByComparingTo(amount);
    }

    @Test
    void twoRecordsWithSameFieldsShouldBeEqual() {
        UUID sagaId = UUID.randomUUID();
        PaymentCommandMessage a = new PaymentCommandMessage(sagaId, "CANCEL", "X", "Y", BigDecimal.ONE);
        PaymentCommandMessage b = new PaymentCommandMessage(sagaId, "CANCEL", "X", "Y", BigDecimal.ONE);

        assertThat(a).isEqualTo(b);
        assertThat(a).hasSameHashCodeAs(b);
    }
}
