package org.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "seats")
@Data @NoArgsConstructor @AllArgsConstructor
public class Seat extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seatNumber;
    private String category; // GOLD, SILVER, etc.
    @ManyToOne @JoinColumn(name = "screen_id")
    private Screen screen;

}
