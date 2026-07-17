package com.rzodeczko.presentation.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GlobalExceptionHandler")
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Nested
    @DisplayName("handle(IllegalArgumentException)")
    class HandleIllegalArgumentException {

        @Test
        void shouldReturnBadRequestWithDetail() {
            IllegalArgumentException exception = new IllegalArgumentException("invalid sagaId");

            ProblemDetail problem = handler.handleBadRequest(exception);

            assertThat(problem.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(problem.getTitle()).isEqualTo("Bad Request");
            assertThat(problem.getDetail()).isEqualTo("invalid sagaId");
            assertThat(problem.getType().toString()).isEqualTo("/problems/bad-request");
        }
    }

    @Nested
    @DisplayName("handle(Exception)")
    class HandleGenericException {

        @Test
        void shouldReturnInternalServerErrorWithGenericMessage() {
            Exception exception = new RuntimeException("something exploded");

            ProblemDetail problem = handler.handleUnexpected(exception);

            assertThat(problem.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            assertThat(problem.getTitle()).isEqualTo("Internal Server Error");
            assertThat(problem.getDetail()).isEqualTo("An unexpected error occurred");
            assertThat(problem.getDetail()).doesNotContain("something exploded");
        }
    }
}
