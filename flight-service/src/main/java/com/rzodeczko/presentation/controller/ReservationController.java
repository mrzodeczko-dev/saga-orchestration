package com.rzodeczko.presentation.controller;

import com.rzodeczko.application.port.in.GetSeatReservationUseCase;
import com.rzodeczko.presentation.dto.response.SeatReservationResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final GetSeatReservationUseCase getSeatReservationUseCase;

    public ReservationController(
            @Qualifier("transactionalSeatReservationQueryService")
            GetSeatReservationUseCase getSeatReservationUseCase) {
        this.getSeatReservationUseCase = getSeatReservationUseCase;
    }

    @GetMapping
    public ResponseEntity<List<SeatReservationResponseDto>> listAll() {
        return ResponseEntity
                .ok(getSeatReservationUseCase
                        .listAll()
                        .stream()
                        .map(SeatReservationResponseDto::from)
                        .toList());
    }

    @GetMapping("/{sagaId}")
    public ResponseEntity<SeatReservationResponseDto> getBySaga(@PathVariable UUID sagaId) {
        return getSeatReservationUseCase
                .getBySagaId(sagaId)
                .map(SeatReservationResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
