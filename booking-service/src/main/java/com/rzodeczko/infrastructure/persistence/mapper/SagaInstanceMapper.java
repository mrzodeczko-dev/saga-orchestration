package com.rzodeczko.infrastructure.persistence.mapper;

import com.rzodeczko.domain.model.saga.SagaInstance;
import com.rzodeczko.infrastructure.persistence.entity.SagaInstanceEntity;
import com.rzodeczko.infrastructure.persistence.entity.SagaStepEntity;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class SagaInstanceMapper {
    public SagaInstanceEntity toNewEntity(SagaInstance saga) {
        SagaInstanceEntity entity = SagaInstanceEntity
                .builder()
                .id(saga.getId())
                .customerName(saga.getCustomerName())
                .destination(saga.getDestination())
                .amount(saga.getAmount())
                .status(saga.getStatus())
                .createdAt(saga.getCreatedAt())
                .updatedAt(saga.getUpdatedAt())
                .build();

        saga.getSteps().forEach(step -> entity.addStep(SagaStepEntity
                .builder()
                .name(step.getName())
                .status(step.getStatus())
                .reason(step.getReason())
                .build()));

        return entity;
    }

    public void updateEntity(SagaInstanceEntity entity, SagaInstance saga) {
        entity.setStatus(saga.getStatus());
        entity.setUpdatedAt(saga.getUpdatedAt());

        entity.getSteps().forEach(stepEntity -> {
            SagaStep domainStep = saga.getStep(stepEntity.getName());
            stepEntity.setStatus(domainStep.getStatus());
            stepEntity.setReason(domainStep.getReason());
        });
    }

    public SagaInstance toDomain(SagaInstanceEntity entity) {
        List<SagaStep> steps = entity
                .getSteps()
                .stream()
                .sorted(Comparator.comparingInt(a -> a.getName().ordinal()))
                .map(e -> SagaStep.restore(e.getName(), e.getStatus(), e.getReason()))
                .toList();

        return SagaInstance.restore(
                entity.getId(),
                entity.getCustomerName(),
                entity.getDestination(),
                entity.getAmount(),
                entity.getStatus(),
                steps,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
