package com.rzodeczko.infrastructure.messaging.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FlightCommandMessageDtoTest {

    @Test
    void shouldExposeAllFieldsViaAccessors() {
        UUID sagaId = UUID.randomUUID();
        FlightCommandMessageDto msg = new FlightCommandMessageDto(
                sagaId, "RESERVE", "Jan", "Mars", new BigDecimal("100"));

        assertThat(msg.sagaId()).isEqualTo(sagaId);
        assertThat(msg.action()).isEqualTo("RESERVE");
        assertThat(msg.customerName()).isEqualTo("Jan");
        assertThat(msg.destination()).isEqualTo("Mars");
        assertThat(msg.amount()).isEqualByComparingTo("100");
    }

    @Test
    void twoRecordsWithSameFieldsShouldBeEqual() {
        UUID sagaId = UUID.randomUUID();
        FlightCommandMessageDto a = new FlightCommandMessageDto(sagaId, "CANCEL", "X", "Y", BigDecimal.ONE);
        FlightCommandMessageDto b = new FlightCommandMessageDto(sagaId, "CANCEL", "X", "Y", BigDecimal.ONE);

        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
    }
}
