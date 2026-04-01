package org.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Table(name = "screens")
@Data @NoArgsConstructor @AllArgsConstructor
public class Screen extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Might be needed to define the physical layout
    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Seat> seats;

    @ManyToOne @JoinColumn(name = "theatre_id")
    private Theatre theatre;

}
