package com.rzodeczko.infrastructure.tx;

import com.rzodeczko.application.command.StartTripBookingCommand;
import com.rzodeczko.application.dto.SagaInstanceDto;
import com.rzodeczko.application.dto.SagaStepDto;
import com.rzodeczko.application.event.ReplyStatus;
import com.rzodeczko.application.event.SagaAction;
import com.rzodeczko.application.event.SagaReply;
import com.rzodeczko.application.service.SagaOrchestratorImpl;
import com.rzodeczko.domain.model.saga.SagaStepName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionalSagaOrchestratorTest {

    @Mock
    private SagaOrchestratorImpl delegate;

    @InjectMocks
    private TransactionalSagaOrchestrator orchestrator;

    @Test
    void handleShouldDelegate() {
        SagaReply reply = new SagaReply(
                UUID.randomUUID(), SagaStepName.FLIGHT, SagaAction.RESERVE, ReplyStatus.SUCCESS, null);

        orchestrator.handle(reply);

        verify(delegate).handle(reply);
    }

    @Test
    void startShouldDelegateAndReturnResult() {
        StartTripBookingCommand cmd = new StartTripBookingCommand("Jan", "Mars", BigDecimal.TEN);
        SagaInstanceDto dto = new SagaInstanceDto(
                "saga-1", "Jan", "Mars", BigDecimal.TEN, "IN_PROGRESS",
                List.of(new SagaStepDto("FLIGHT", "PENDING", null)),
                Instant.now(), Instant.now());
        when(delegate.start(cmd)).thenReturn(dto);

        SagaInstanceDto result = orchestrator.start(cmd);

        assertThat(result).isEqualTo(dto);
        verify(delegate).start(cmd);
    }
}
