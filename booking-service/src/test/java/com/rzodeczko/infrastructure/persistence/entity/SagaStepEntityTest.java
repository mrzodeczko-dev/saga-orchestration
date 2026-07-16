package com.rzodeczko.infrastructure.persistence.entity;

import com.rzodeczko.domain.model.saga.SagaStepName;
import com.rzodeczko.domain.model.saga.SagaStepStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SagaStepEntityTest {

    @Test
    void builderShouldSetAllFields() {
        SagaInstanceEntity saga = SagaInstanceEntity.builder().build();

        SagaStepEntity step = SagaStepEntity.builder()
                .id(1L)
                .saga(saga)
                .name(SagaStepName.FLIGHT)
                .status(SagaStepStatus.RESERVED)
                .reason("ok")
                .build();

        assertThat(step.getId()).isEqualTo(1L);
        assertThat(step.getSaga()).isSameAs(saga);
        assertThat(step.getName()).isEqualTo(SagaStepName.FLIGHT);
        assertThat(step.getStatus()).isEqualTo(SagaStepStatus.RESERVED);
        assertThat(step.getReason()).isEqualTo("ok");
    }

    @Test
    void equalsShouldUseIdOnly() {
        SagaStepEntity a = SagaStepEntity.builder().id(42L).name(SagaStepName.FLIGHT).build();
        SagaStepEntity b = SagaStepEntity.builder().id(42L).name(SagaStepName.HOTEL).build();

        assertThat(a).isEqualTo(b).hasSameHashCodeAs(b);
    }

    @Test
    void twoEntitiesWithDifferentIdShouldNotBeEqual() {
        SagaStepEntity a = SagaStepEntity.builder().id(1L).build();
        SagaStepEntity b = SagaStepEntity.builder().id(2L).build();

        assertThat(a).isNotEqualTo(b);
    }

    @Test
    void settersShouldUpdateFields() {
        SagaStepEntity step = new SagaStepEntity();

        step.setName(SagaStepName.PAYMENT);
        step.setStatus(SagaStepStatus.FAILED);
        step.setReason("declined");

        assertThat(step.getName()).isEqualTo(SagaStepName.PAYMENT);
        assertThat(step.getStatus()).isEqualTo(SagaStepStatus.FAILED);
        assertThat(step.getReason()).isEqualTo("declined");
    }
}
