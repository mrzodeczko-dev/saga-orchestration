package com.rzodeczko.application.command;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class StartTripBookingCommandTest {

    @Test
    void shouldExposeAllFields() {
        StartTripBookingCommand cmd = new StartTripBookingCommand("Jan", "Mars", new BigDecimal("1000"));

        assertThat(cmd.customerName()).isEqualTo("Jan");
        assertThat(cmd.destination()).isEqualTo("Mars");
        assertThat(cmd.amount()).isEqualByComparingTo("1000");
    }

    @Test
    void twoRecordsWithSameFieldsShouldBeEqual() {
        StartTripBookingCommand a = new StartTripBookingCommand("Jan", "Mars", BigDecimal.TEN);
        StartTripBookingCommand b = new StartTripBookingCommand("Jan", "Mars", BigDecimal.TEN);

        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
    }
}
