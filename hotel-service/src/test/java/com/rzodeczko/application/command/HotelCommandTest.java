package com.rzodeczko.application.command;

import com.rzodeczko.application.event.SagaAction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HotelCommandTest {

    @Test
    void shouldExposeAllFieldsViaAccessors() {
        UUID sagaId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("500.00");

        HotelCommand command = new HotelCommand(
                sagaId, SagaAction.RESERVE, "Jan Kowalski", "Mars", amount);

        assertThat(command.sagaId()).isEqualTo(sagaId);
        assertThat(command.action()).isEqualTo(SagaAction.RESERVE);
        assertThat(command.customerName()).isEqualTo("Jan Kowalski");
        assertThat(command.destination()).isEqualTo("Mars");
        assertThat(command.amount()).isEqualByComparingTo(amount);
    }

    @Test
    void shouldSupportCancelAction() {
        HotelCommand command = new HotelCommand(
                UUID.randomUUID(), SagaAction.CANCEL, "Anna", "Venus", BigDecimal.TEN);

        assertThat(command.action()).isEqualTo(SagaAction.CANCEL);
    }
}
