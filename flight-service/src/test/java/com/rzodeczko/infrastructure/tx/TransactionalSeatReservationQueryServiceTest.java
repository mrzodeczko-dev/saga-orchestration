package com.rzodeczko.infrastructure.tx;

import com.rzodeczko.application.dto.SeatReservationDto;
import com.rzodeczko.application.service.SeatReservationQueryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionalSeatReservationQueryServiceTest {

    @Mock
    private SeatReservationQueryServiceImpl delegate;

    @InjectMocks
    private TransactionalSeatReservationQueryService service;

    private SeatReservationDto sampleDto(String customer) {
        return new SeatReservationDto("id", "saga", customer, "Mars", "RESERVED", Instant.now());
    }

    @Test
    void listAllShouldDelegateAndReturnResult() {
        SeatReservationDto dto = sampleDto("Jan");
        when(delegate.listAll()).thenReturn(List.of(dto));

        List<SeatReservationDto> result = service.listAll();

        assertThat(result).containsExactly(dto);
        verify(delegate).listAll();
    }

    @Test
    void listAllShouldReturnEmptyWhenDelegateReturnsEmpty() {
        when(delegate.listAll()).thenReturn(List.of());

        assertThat(service.listAll()).isEmpty();
    }

    @Test
    void getBySagaIdShouldDelegateAndReturnResult() {
        UUID sagaId = UUID.randomUUID();
        SeatReservationDto dto = sampleDto("Anna");
        when(delegate.getBySagaId(sagaId)).thenReturn(Optional.of(dto));

        Optional<SeatReservationDto> result = service.getBySagaId(sagaId);

        assertThat(result).contains(dto);
        verify(delegate).getBySagaId(sagaId);
    }

    @Test
    void getBySagaIdShouldReturnEmptyWhenReservationNotFound() {
        UUID sagaId = UUID.randomUUID();
        when(delegate.getBySagaId(sagaId)).thenReturn(Optional.empty());

        assertThat(service.getBySagaId(sagaId)).isEmpty();
    }
}
