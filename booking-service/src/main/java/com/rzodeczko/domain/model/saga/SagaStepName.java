package com.rzodeczko.domain.model.saga;

import java.util.Optional;

public enum SagaStepName {
    FLIGHT,
    HOTEL,
    PAYMENT;

    public Optional<SagaStepName> next() {
        int idx = ordinal() + 1;
        SagaStepName[] values = values();
        return idx < values.length ? Optional.of(values[idx]) : Optional.empty();
    }
}
