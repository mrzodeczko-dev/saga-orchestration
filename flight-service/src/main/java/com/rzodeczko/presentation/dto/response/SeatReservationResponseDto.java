package com.rzodeczko.presentation.dto.response;


import com.rzodeczko.application.dto.SeatReservationDto;

import java.time.Instant;

public record SeatReservationResponseDto(
        String id,
        String sagaId,
        String customerName,
        String destination,
        String status,
        Instant createdAt
) {
    public static SeatReservationResponseDto from(SeatReservationDto dto) {
        return new SeatReservationResponseDto(
                dto.id(),
                dto.sagaId(),
                dto.customerName(),
                dto.destination(),
                dto.status(),
                dto.createdAt()
        );
    }

}
