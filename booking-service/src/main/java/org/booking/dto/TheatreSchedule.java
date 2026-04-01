package org.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TheatreSchedule(
        String theatreName,
        LocalDateTime startTime,
        BigDecimal basePrice,
        BigDecimal finalPrice,
        String offerApplied
) {}
