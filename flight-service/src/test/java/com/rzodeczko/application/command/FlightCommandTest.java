package com.rzodeczko.application.command;

import com.rzodeczko.application.event.SagaAction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FlightCommandTest {

    @Test
    void shouldExposeAllFieldsViaAccessors() {
        UUID sagaId = UUID.randomUUID();

        FlightCommand cmd = new FlightCommand(
                sagaId, SagaAction.RESERVE, "Jan", "Mars", new BigDecimal("100"));

        assertThat(cmd.sagaId()).isEqualTo(sagaId);
        assertThat(cmd.action()).isEqualTo(SagaAction.RESERVE);
        assertThat(cmd.customerName()).isEqualTo("Jan");
        assertThat(cmd.destination()).isEqualTo("Mars");
        assertThat(cmd.amount()).isEqualByComparingTo("100");
    }

    @Test
    void twoCommandsWithSameFieldsShouldBeEqual() {
        UUID sagaId = UUID.randomUUID();
        FlightCommand a = new FlightCommand(sagaId, SagaAction.CANCEL, "X", "Y", BigDecimal.ONE);
        FlightCommand b = new FlightCommand(sagaId, SagaAction.CANCEL, "X", "Y", BigDecimal.ONE);

        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
    }
}
