package com.rzodeczko.application.dto;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PageResultTest {

    @Test
    void shouldExposeAllFields() {
        PageResult<String> result = new PageResult<>(List.of("a", "b"), 0, 20, 42);

        assertThat(result.content()).containsExactly("a", "b");
        assertThat(result.page()).isZero();
        assertThat(result.size()).isEqualTo(20);
        assertThat(result.totalElements()).isEqualTo(42);
    }

    @Test
    void shouldSupportEmptyContent() {
        PageResult<String> result = new PageResult<>(List.of(), 5, 10, 0);

        assertThat(result.content()).isEmpty();
        assertThat(result.page()).isEqualTo(5);
        assertThat(result.totalElements()).isZero();
    }
}
