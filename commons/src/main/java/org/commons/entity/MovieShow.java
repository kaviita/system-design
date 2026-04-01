package org.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "shows")
@Data @NoArgsConstructor @AllArgsConstructor
public class MovieShow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal basePrice;
    @ManyToOne @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne @JoinColumn(name = "screen_id")
    private Screen screen;
    // Lifecycle Status: ACTIVE, CANCELLED, COMPLETED
    private String status;

}
