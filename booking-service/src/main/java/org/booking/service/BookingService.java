package org.booking.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.booking.dto.BookingRequestDto;
import org.booking.dto.BookingResponseDto;
import org.booking.dto.ShowTimeDto;
import org.booking.dto.TheatreShowsDto;
import org.booking.entity.BookingStatus;
import org.booking.entity.SeatStatus;
import org.commons.entity.*;
import org.commons.repository.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final StringRedisTemplate redisTemplate;

    public List<TheatreShowsDto> browseShows(Long movieId, String city, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        // Find shows matching movie, city, and time range
        List<MovieShow> shows = showRepository.findShowsByFilters(movieId, city, startOfDay, endOfDay);

        return shows.stream()
                .collect(Collectors.groupingBy(show -> show.getScreen().getTheatre()))
                .entrySet().stream()
                .map(entry -> {
                    Theatre theatre = entry.getKey();
                    List<ShowTimeDto> showTimes = entry.getValue().stream()
                            .map(s -> new ShowTimeDto(s.getId(), s.getStartTime(), s.getBasePrice(), s.getScreen().getName(), s.getStatus()))
                            .sorted(Comparator.comparing(ShowTimeDto::startTime)) // Sort by time
                            .toList();

                    return new TheatreShowsDto(theatre.getId(), theatre.getName(), theatre.getAddress(), showTimes);
                })
                .toList();
    }

    @Transactional
    public BookingResponseDto createBooking(BookingRequestDto request) {

        try{
            MovieShow show = showRepository.findById(request.showId())
                    .orElseThrow(() -> new EntityNotFoundException("Show not found"));

            List<ShowSeat> seats = showSeatRepository.findAllById(request.seatIds());

            // Validation & Locking
            validateAndLockSeats(seats);

            // Apply Discount Strategies
            BigDecimal finalAmount = calculateFinalPrice(show, seats);

            // Persistence
            Booking booking = new Booking();
            booking.setShow(show);
            booking.setTotalAmount(finalAmount);
            booking.setBookingStatus(String.valueOf(BookingStatus.CONFIRMED));
            Booking savedBooking = bookingRepository.save(booking);

            seats.forEach(s -> s.setBooking(savedBooking));
            showSeatRepository.saveAll(seats);

            return mapToResponse(savedBooking, seats);
        }catch (Exception exception){
            throw exception;
        }



//        // 1. REDIS LOCKING: Try to lock all seats
//        List<String> lockKeys = request.seatIds().stream()
//                .map(id -> "lock:seat:" + id)
//                .toList();
//
//        // Simple Redis lock implementation directly in the method
//        Boolean allLocked = redisTemplate.opsForValue()
//                .multiSetIfAbsent(lockKeys.stream().collect(Collectors.toMap(k -> k, k -> "LOCKED")));
//
//        if (Boolean.FALSE.equals(allLocked)) {
//            throw new RuntimeException("One or more seats are currently being booked by someone else.");
//        }
//
//        // Set TTL for the locks so they don't stay forever if the app crashes
//        lockKeys.forEach(key -> redisTemplate.expire(key, Duration.ofMinutes(10)));

        // redisTemplate.delete(lockKeys) in catch.
    }

    private void validateAndLockSeats(List<ShowSeat> seats) {
        for (ShowSeat seat : seats) {
            if (!seat.getStatus().equals(String.valueOf(SeatStatus.AVAILABLE))) {
                throw new IllegalStateException("Seat " + seat.getId() + " is unavailable");
            }
            seat.setStatus("BOOKED"); // This triggers the @Version increment in DB
        }
    }

    private BigDecimal calculateFinalPrice(MovieShow show, List<ShowSeat> seats) {
        // 1. Start with the Gross Total
        BigDecimal basePrice = show.getBasePrice();
        BigDecimal grossTotal = basePrice.multiply(new BigDecimal(seats.size()));

        BigDecimal totalDiscounts = BigDecimal.ZERO;

        List<DiscountStrategy> strategies = List.of(
                new AfternoonDiscountStrategy(),
                new ThirdTicketDiscountStrategy()
        );

        // 3. Aggregate all applicable discounts
        for (DiscountStrategy strategy : strategies) {
            totalDiscounts = totalDiscounts.add(strategy.applyDiscount(seats, show));
        }

        // 4. Return Final Amount (Gross - Total Discounts)
        return grossTotal.subtract(totalDiscounts);
    }

    private BookingResponseDto mapToResponse(Booking booking, List<ShowSeat> seats) {
        List<String> seatNumbers = seats.stream()
                .map(s -> s.getSeat().getSeatNumber())
                .toList();

        return new BookingResponseDto(
                booking.getId(),
                booking.getShow().getMovie().getTitle(),
                booking.getShow().getScreen().getTheatre().getName(),
                booking.getShow().getStartTime(),
                seatNumbers,
                booking.getTotalAmount(),
                booking.getBookingStatus()
        );
    }
}