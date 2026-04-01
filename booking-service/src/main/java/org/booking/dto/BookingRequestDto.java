package org.booking.dto;

import java.util.List;

public record BookingRequestDto(

        Long showId,
        List<Long> seatIds,
        Long customerId

) {}
