package com.rzodeczko.infrastructure.tx;

import com.rzodeczko.application.command.FlightCommand;
import com.rzodeczko.application.event.SagaAction;
import com.rzodeczko.application.service.FlightCommandService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class TransactionalFlightCommandServiceTest {

    @Mock
    private FlightCommandService delegate;

    @InjectMocks
    private TransactionalFlightCommandService service;

    @Test
    void handleShouldDelegateToInnerService() {
        FlightCommand command = new FlightCommand(
                UUID.randomUUID(), SagaAction.RESERVE, "Jan", "Mars", BigDecimal.TEN);

        service.handle(command);

        verify(delegate).handle(command);
        verifyNoMoreInteractions(delegate);
    }
}
