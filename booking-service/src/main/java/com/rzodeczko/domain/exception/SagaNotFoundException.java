package com.rzodeczko.domain.exception;

import java.util.UUID;

public class SagaNotFoundException extends RuntimeException {
    public SagaNotFoundException(UUID sagaId) {
        super("Saga not found: " + sagaId);
    }
}
