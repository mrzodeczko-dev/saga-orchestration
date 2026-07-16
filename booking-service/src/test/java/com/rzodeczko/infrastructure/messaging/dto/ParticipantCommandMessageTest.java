package com.rzodeczko.infrastructure.messaging.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ParticipantCommandMessageTest {

    @Test
    void shouldExposeAllFields() {
        UUID sagaId = UUID.randomUUID();
        ParticipantCommandMessage msg = new ParticipantCommandMessage(
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
        ParticipantCommandMessage a = new ParticipantCommandMessage(sagaId, "CANCEL", "X", "Y", BigDecimal.ONE);
        ParticipantCommandMessage b = new ParticipantCommandMessage(sagaId, "CANCEL", "X", "Y", BigDecimal.ONE);

        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
    }
}
