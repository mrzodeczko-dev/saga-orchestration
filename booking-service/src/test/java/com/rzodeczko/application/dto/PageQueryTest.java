package com.rzodeczko.application.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PageQueryTest {

    @Test
    void shouldAcceptValidValues() {
        PageQuery q = new PageQuery(0, 20);

        assertThat(q.page()).isZero();
        assertThat(q.size()).isEqualTo(20);
    }

    @Test
    void shouldAcceptMinSize() {
        assertThat(new PageQuery(0, 1).size()).isEqualTo(1);
    }

    @Test
    void shouldAcceptMaxSize() {
        assertThat(new PageQuery(0, 100).size()).isEqualTo(100);
    }

    @Test
    void shouldRejectSizeZero() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PageQuery(0, 0))
                .withMessageContaining("between 1 and 100");
    }

    @Test
    void shouldRejectNegativeSize() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PageQuery(0, -1))
                .withMessageContaining("between 1 and 100");
    }

    @Test
    void shouldRejectSizeAbove100() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new PageQuery(0, 101))
                .withMessageContaining("between 1 and 100");
    }
}
