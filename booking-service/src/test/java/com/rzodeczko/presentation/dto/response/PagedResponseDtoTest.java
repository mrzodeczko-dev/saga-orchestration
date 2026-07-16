package com.rzodeczko.presentation.dto.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PagedResponseDtoTest {

    @Test
    void shouldExposeAllFields() {
        PagedResponseDto<String> page = new PagedResponseDto<>(List.of("x", "y"), 1, 10, 15);

        assertThat(page.content()).containsExactly("x", "y");
        assertThat(page.page()).isEqualTo(1);
        assertThat(page.size()).isEqualTo(10);
        assertThat(page.totalElements()).isEqualTo(15);
    }
}
