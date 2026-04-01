package org.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;

@Entity @Table(name = "bookings")
@Data @NoArgsConstructor @AllArgsConstructor
public class Booking extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal totalAmount;
    private String bookingStatus;
    @ManyToOne @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne @JoinColumn(name = "show_id")
    private MovieShow show;
    @OneToMany(mappedBy = "booking", cascade = CascadeType.PERSIST)
    private List<ShowSeat> showSeats; // For Bulk booking and cancellation

}
