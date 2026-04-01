package org.partner.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.booking.entity.SeatStatus;
import org.booking.entity.ShowStatus;
import org.commons.entity.*;
import org.commons.repository.MovieRepository;
import org.commons.repository.ScreenRepository;
import org.commons.repository.ShowRepository;
import org.commons.repository.ShowSeatRepository;
import org.partner.dto.ShowCreateDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service @RequiredArgsConstructor
public class ShowManagementService {

    private final ShowRepository showRepository;
    private final ScreenRepository screenRepository;
    private final MovieRepository movieRepository;
    private final ShowSeatRepository showSeatRepository;

    // --- CREATE ---
    @Transactional
    public MovieShow createShow(ShowCreateDto showCreateDto) {

        Screen screen = screenRepository.findById(showCreateDto.screenId())
                .orElseThrow(() -> new EntityNotFoundException("Screen not found"));
        Movie movie = movieRepository.findById(showCreateDto.movieId())
                .orElseThrow(() -> new EntityNotFoundException("Movie not found"));

        // 1. Validation: Prevent double-booking a Physical Screen
        boolean isOverlapping = showRepository.existsOverlap(
                showCreateDto.screenId(), showCreateDto.startTime(), showCreateDto.endTime());
        if (isOverlapping) {
            throw new IllegalStateException(
                    "Screen " + showCreateDto.screenId() + " is already booked between " +
                            showCreateDto.startTime() + " and " + showCreateDto.endTime()
            );
        }

        // 2. Save the Show
        MovieShow show = new MovieShow();
        show.setScreen(screen);
        show.setStartTime(showCreateDto.startTime());
        show.setEndTime(showCreateDto.endTime());
        show.setStatus("ACTIVE");
        MovieShow savedShow = showRepository.save(show);

        // 3. Inventory Allocation: Create ShowSeats from Screen layout
        allocateSeatInventory(savedShow, screen);

        return savedShow;
    }

    private void allocateSeatInventory(MovieShow show, Screen screen) {
        // Fetch all physical seats for this screen
        List<Seat> physicalSeats = screen.getSeats();
        // Map physical layout to this specific show's inventory
        List<ShowSeat> showSeats = physicalSeats.stream().map(ps -> {
            ShowSeat ss = new ShowSeat();
            ss.setShow(show);
            ss.setSeat(ps);
            ss.setStatus(String.valueOf(SeatStatus.AVAILABLE));
            ss.setPriceMultiplier(BigDecimal.ONE); // Can be adjusted for VIP/Luxury
            return ss;
        }).toList();

        showSeatRepository.saveAll(showSeats);

    }

//    private void cancelInventory(Long showId) {
//        List<ShowSeat> seats = showSeatRepository.findByShow_Id(showId);
//        seats.forEach(s -> s.setStatus("CANCELLED"));
//        showSeatRepository.saveAll(seats);
//    }

}
