package org.booking.controller;

import lombok.RequiredArgsConstructor;
import org.booking.dto.BookingRequestDto;
import org.booking.dto.BookingResponseDto;
import org.booking.dto.TheatreShowsDto;
import org.booking.service.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/catalog/browse")
    public ResponseEntity<List<TheatreShowsDto>> browseShows(
            @RequestParam String city,
            @RequestParam Long movieId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(bookingService.browseShows(movieId, city, date));
    }

    @PostMapping("/bookings")
    public ResponseEntity<BookingResponseDto> bookTickets(@RequestBody BookingRequestDto request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

}
