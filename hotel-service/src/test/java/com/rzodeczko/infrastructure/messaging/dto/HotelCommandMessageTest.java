package com.rzodeczko.infrastructure.messaging.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HotelCommandMessageTest {

    @Test
    void shouldExposeAllFieldsViaAccessors() {
        UUID sagaId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("999.99");

        HotelCommandMessage msg = new HotelCommandMessage(
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
        HotelCommandMessage a = new HotelCommandMessage(sagaId, "CANCEL", "X", "Y", BigDecimal.ONE);
        HotelCommandMessage b = new HotelCommandMessage(sagaId, "CANCEL", "X", "Y", BigDecimal.ONE);

        assertThat(a).isEqualTo(b);
        assertThat(a).hasSameHashCodeAs(b);
    }
}
