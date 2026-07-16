package com.rzodeczko.application.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReplyStatusTest {

    @Test
    void shouldContainSuccessAndFailure() {
        assertThat(ReplyStatus.values()).containsExactly(ReplyStatus.SUCCESS, ReplyStatus.FAILURE);
    }

    @Test
    void valueOfShouldMatchEnumName() {
        assertThat(ReplyStatus.valueOf("SUCCESS")).isEqualTo(ReplyStatus.SUCCESS);
        assertThat(ReplyStatus.valueOf("FAILURE")).isEqualTo(ReplyStatus.FAILURE);
    }
}
