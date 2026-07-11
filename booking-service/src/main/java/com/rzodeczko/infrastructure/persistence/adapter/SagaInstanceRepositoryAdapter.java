package com.rzodeczko.infrastructure.persistence.adapter;

import com.rzodeczko.application.port.out.SagaInstanceRepository;
import com.rzodeczko.domain.model.saga.SagaInstance;
import com.rzodeczko.infrastructure.persistence.entity.SagaInstanceEntity;
import com.rzodeczko.infrastructure.persistence.mapper.SagaInstanceMapper;
import com.rzodeczko.infrastructure.persistence.repository.JpaSagaInstanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SagaInstanceRepositoryAdapter implements SagaInstanceRepository {

    private final JpaSagaInstanceRepository jpaSagaInstanceRepository;
    private final SagaInstanceMapper mapper;

    @Override
    public void save(SagaInstance saga) {
        SagaInstanceEntity entity = jpaSagaInstanceRepository.findById(saga.getId())
                .map(existing -> {
                    mapper.updateEntity(existing, saga);
                    return existing;
                })
                .orElseGet(() -> mapper.toNewEntity(saga));
        jpaSagaInstanceRepository.save(entity);
    }

    @Override
    public Optional<SagaInstance> findById(UUID sagaId) {
        return jpaSagaInstanceRepository.findById(sagaId).map(mapper::toDomain);
    }

    @Override
    public Optional<SagaInstance> findByIdForUpdate(UUID sagaId) {
        return jpaSagaInstanceRepository.findByIdForUpdate(sagaId).map(mapper::toDomain);
    }

    @Override
    public List<SagaInstance> findAll() {
        return jpaSagaInstanceRepository
                .findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
