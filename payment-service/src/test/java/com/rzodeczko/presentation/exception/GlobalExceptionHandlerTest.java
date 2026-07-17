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
    @DisplayName("IllegalArgumentException handler")
    class IllegalArgumentHandling {

        @Test
        @DisplayName("maps to 400 Bad Request with the exception message")
        void mapsToBadRequest() {
            IllegalArgumentException exception = new IllegalArgumentException("invalid input");

            ProblemDetail problem = handler.handleBadRequest(exception);

            assertThat(problem.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(problem.getTitle()).isEqualTo("Bad Request");
            assertThat(problem.getDetail()).isEqualTo("invalid input");
            assertThat(problem.getType().toString()).isEqualTo("/problems/bad-request");
        }
    }

    @Nested
    @DisplayName("generic Exception handler")
    class GenericExceptionHandling {

        @Test
        @DisplayName("maps to 500 Internal Server Error with a generic message, hiding exception details")
        void mapsToInternalServerError() {
            Exception exception = new RuntimeException("some sensitive internal detail");

            ProblemDetail problem = handler.handleUnexpected(exception);

            assertThat(problem.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            assertThat(problem.getTitle()).isEqualTo("Internal Server Error");
            assertThat(problem.getDetail()).isEqualTo("An unexpected error occurred");
            assertThat(problem.getDetail()).doesNotContain("some sensitive internal detail");
        }
    }
}
