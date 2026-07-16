package com.rzodeczko.application.event;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CommandResultTest {

    @Test
    void successShouldCreateSuccessfulResultWithoutReason() {
        CommandResult result = CommandResult.success();

        assertThat(result.succeeded()).isTrue();
        assertThat(result.reason()).isNull();
        assertThat(result.statusString()).isEqualTo("SUCCESS");
    }

    @Test
    void failureShouldCreateFailedResultWithReason() {
        CommandResult result = CommandResult.failure("no seats available");

        assertThat(result.succeeded()).isFalse();
        assertThat(result.reason()).isEqualTo("no seats available");
        assertThat(result.statusString()).isEqualTo("FAILURE");
    }

    @Test
    void statusStringShouldBeFailureWhenSucceededIsFalse() {
        assertThat(new CommandResult(false, "").statusString()).isEqualTo("FAILURE");
    }

    @Test
    void statusStringShouldBeSuccessWhenSucceededIsTrue() {
        assertThat(new CommandResult(true, null).statusString()).isEqualTo("SUCCESS");
    }
}
