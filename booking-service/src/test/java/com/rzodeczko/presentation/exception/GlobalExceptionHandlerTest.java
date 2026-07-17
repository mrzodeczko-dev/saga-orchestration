package com.rzodeczko.presentation.exception;

import com.rzodeczko.domain.exception.InvalidSagaStateException;
import com.rzodeczko.domain.exception.SagaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Nested
    @DisplayName("SagaNotFoundException")
    class SagaNotFound {

        @Test
        @DisplayName("should return 404 with saga id in detail")
        void shouldReturn404() {
            UUID sagaId = UUID.randomUUID();
            SagaNotFoundException ex = new SagaNotFoundException(sagaId);

            ProblemDetail problem = handler.handleSagaNotFound(ex);

            assertThat(problem.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
            assertThat(problem.getTitle()).isEqualTo("Not Found");
            assertThat(problem.getDetail()).contains(sagaId.toString());
            assertThat(problem.getType().toString()).isEqualTo("/problems/saga-not-found");
        }
    }

    @Nested
    @DisplayName("IllegalArgumentException / InvalidSagaStateException")
    class BadRequest {

        @Test
        @DisplayName("should return 400 for IllegalArgumentException")
        void shouldReturn400ForIllegalArgument() {
            IllegalArgumentException ex = new IllegalArgumentException("bad input");

            ProblemDetail problem = handler.handleBadRequest(ex);

            assertThat(problem.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(problem.getTitle()).isEqualTo("Bad Request");
            assertThat(problem.getDetail()).isEqualTo("bad input");
        }

        @Test
        @DisplayName("should return 400 for InvalidSagaStateException")
        void shouldReturn400ForInvalidSagaState() {
            InvalidSagaStateException ex = new InvalidSagaStateException("invalid state");

            ProblemDetail problem = handler.handleBadRequest(ex);

            assertThat(problem.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(problem.getDetail()).isEqualTo("invalid state");
        }
    }

    @Nested
    @DisplayName("Generic Exception")
    class GenericException {

        @Test
        @DisplayName("should return 500 with generic message, hiding exception details")
        void shouldReturn500() {
            Exception ex = new Exception("something broke");

            ProblemDetail problem = handler.handleUnexpected(ex);

            assertThat(problem.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
            assertThat(problem.getTitle()).isEqualTo("Internal Server Error");
            assertThat(problem.getDetail()).isEqualTo("An unexpected error occurred");
            assertThat(problem.getDetail()).doesNotContain("something broke");
        }
    }
}
