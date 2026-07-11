package com.rzodeczko.infrastructure.tx;

import com.rzodeczko.application.command.StartTripBookingCommand;
import com.rzodeczko.application.dto.SagaInstanceDto;
import com.rzodeczko.application.event.SagaReply;
import com.rzodeczko.application.port.in.HandleSagaReplyUseCase;
import com.rzodeczko.application.port.in.StartTripBookingUseCase;
import com.rzodeczko.application.service.SagaOrchestratorImpl;
import org.springframework.transaction.annotation.Transactional;

public class TransactionalSagaOrchestrator implements StartTripBookingUseCase, HandleSagaReplyUseCase {

    private final SagaOrchestratorImpl delegate;

    public TransactionalSagaOrchestrator(SagaOrchestratorImpl delegate) {
        this.delegate = delegate;
    }

    @Override
    @Transactional
    public void handle(SagaReply reply) {
        delegate.handle(reply);
    }

    @Override
    @Transactional
    public SagaInstanceDto start(StartTripBookingCommand command) {
        return delegate.start(command);
    }
}
