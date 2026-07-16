package com.rzodeczko.infrastructure.persistence.entity;

import com.rzodeczko.domain.model.saga.SagaStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SagaInstanceEntityTest {

    @Test
    void builderShouldSetAllFields() {
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        SagaInstanceEntity entity = SagaInstanceEntity.builder()
                .id(id)
                .customerName("Jan")
                .destination("Mars")
                .amount(new BigDecimal("1000"))
                .status(SagaStatus.IN_PROGRESS)
                .createdAt(now)
                .updatedAt(now)
                .build();

        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getCustomerName()).isEqualTo("Jan");
        assertThat(entity.getDestination()).isEqualTo("Mars");
        assertThat(entity.getAmount()).isEqualByComparingTo("1000");
        assertThat(entity.getStatus()).isEqualTo(SagaStatus.IN_PROGRESS);
        assertThat(entity.getSteps()).isNotNull().isEmpty();
    }

    @Test
    void addStepShouldSetBidirectionalRelation() {
        SagaInstanceEntity entity = SagaInstanceEntity.builder()
                .id(UUID.randomUUID())
                .customerName("A").destination("B").amount(BigDecimal.ONE)
                .status(SagaStatus.IN_PROGRESS)
                .createdAt(Instant.now()).updatedAt(Instant.now())
                .steps(new ArrayList<>())
                .build();
        SagaStepEntity step = new SagaStepEntity();

        entity.addStep(step);

        assertThat(entity.getSteps()).containsExactly(step);
        assertThat(step.getSaga()).isEqualTo(entity);
    }

    @Test
    void equalsShouldUseIdOnly() {
        UUID id = UUID.randomUUID();
        SagaInstanceEntity a = SagaInstanceEntity.builder().id(id).build();
        SagaInstanceEntity b = SagaInstanceEntity.builder().id(id).customerName("different").build();

        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
    }

    @Test
    void settersShouldUpdateFields() {
        SagaInstanceEntity entity = new SagaInstanceEntity();
        entity.setCustomerName("X");
        entity.setStatus(SagaStatus.COMPLETED);
        entity.setSteps(List.of());

        assertThat(entity.getCustomerName()).isEqualTo("X");
        assertThat(entity.getStatus()).isEqualTo(SagaStatus.COMPLETED);
    }
}
