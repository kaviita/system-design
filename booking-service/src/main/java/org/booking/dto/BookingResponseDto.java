package org.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingResponseDto(

        Long bookingId,
        String movieTitle,
        String theatreName,
        LocalDateTime startTime,
        List<String> seatNumbers,
        BigDecimal totalAmount,
        String status

) {}
