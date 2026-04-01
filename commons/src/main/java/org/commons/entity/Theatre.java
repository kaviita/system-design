package org.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "theatres")
@Data @NoArgsConstructor @AllArgsConstructor
public class Theatre {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String city;
    @ManyToOne @JoinColumn(name = "partner_id")
    private Partner partner;

}
