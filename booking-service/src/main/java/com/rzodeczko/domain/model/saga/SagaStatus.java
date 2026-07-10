package com.rzodeczko.domain.model.saga;

public enum SagaStatus {
    IN_PROGRESS,
    COMPLETED,
    COMPENSATING,
    CANCELLED,
    COMPENSATION_FAILED
}
