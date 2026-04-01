package org.commons.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity @Table(name = "partners")
@Data @NoArgsConstructor @AllArgsConstructor
public class Partner extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mobileNumber;
    private String emailAddress;
    private LocalDate onboardingDate;

}
