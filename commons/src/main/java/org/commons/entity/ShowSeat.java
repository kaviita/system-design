package org.commons.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;import java.math.BigDecimal;

@Entity @Table(name = "show_seats")
@Data @NoArgsConstructor
public class ShowSeat {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne @JoinColumn(name = "show_id")
    private MovieShow show;
    @ManyToOne @JoinColumn(name = "seat_id")
    private Seat seat;
    private String status; // AVAILABLE, BOOKED
    private BigDecimal priceMultiplier;
    @Version
    private Integer version;
    @ManyToOne @JoinColumn(name = "booking_id")
    private Booking booking; // Null if AVAILABLE

}
