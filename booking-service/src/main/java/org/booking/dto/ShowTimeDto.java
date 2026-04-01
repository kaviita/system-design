package org.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShowTimeDto(
        Long showId,
        LocalDateTime startTime,
        BigDecimal basePrice,
        String screenName,
        String status
) {
}
