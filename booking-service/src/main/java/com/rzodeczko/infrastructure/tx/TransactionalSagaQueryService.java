package com.rzodeczko.infrastructure.tx;

import com.rzodeczko.application.dto.SagaInstanceDto;
import com.rzodeczko.application.port.in.GetSagaUseCase;
import com.rzodeczko.application.service.SagaQueryServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public class TransactionalSagaQueryService implements GetSagaUseCase {

    private final SagaQueryServiceImpl delegate;

    public TransactionalSagaQueryService(SagaQueryServiceImpl delegate) {
        this.delegate = delegate;
    }

    @Override
    @Transactional(readOnly = true)
    public SagaInstanceDto getById(UUID sagaId) {
        return delegate.getById(sagaId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SagaInstanceDto> listAll() {
        return delegate.listAll();
    }
}
