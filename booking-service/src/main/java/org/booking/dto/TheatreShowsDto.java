package org.booking.dto;

import java.util.List;

public record TheatreShowsDto(
        Long theatreId,
        String theatreName,
        String address,
        List<ShowTimeDto> availableShows
) {
}
