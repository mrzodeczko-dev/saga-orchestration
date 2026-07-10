package com.rzodeczko.domain.model.saga;

public enum SagaStepStatus {
    PENDING,
    RESERVED,
    FAILED,
    COMPENSATED,
    COMPENSATION_FAILED
}
