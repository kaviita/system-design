package org.partner.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShowCreateDto(

        @NotNull(message = "Movie ID is required")
        Long movieId,
        @NotNull(message = "Screen ID is required")
        Long screenId,
        @NotNull(message = "Start time is required") @Future(message = "Show must be scheduled in the future")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime startTime,
        @NotNull(message = "End time is required") @Future(message = "Show must be scheduled in the future")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime endTime,
        @Positive(message = "Base price must be greater than zero")
        BigDecimal basePrice

) {}
