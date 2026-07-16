package com.rzodeczko.domain.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReservationStatusTest {

    @Test
    void shouldContainReservedAndCancelled() {
        assertThat(ReservationStatus.values()).containsExactly(
                ReservationStatus.RESERVED, ReservationStatus.CANCELLED);
    }

    @Test
    void valueOfShouldMatchEnumName() {
        assertThat(ReservationStatus.valueOf("RESERVED")).isEqualTo(ReservationStatus.RESERVED);
        assertThat(ReservationStatus.valueOf("CANCELLED")).isEqualTo(ReservationStatus.CANCELLED);
    }
}
