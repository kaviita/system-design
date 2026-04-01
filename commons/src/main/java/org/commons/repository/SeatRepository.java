package org.commons.repository;

import org.commons.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<ShowSeat, Long> {

    // Standard JPA method. Hibernate checks the 'version' column
    // automatically when saveAll() is called later.
    List<ShowSeat> findAllById(Iterable<Long> ids);

    // Used for showing seat maps
    List<ShowSeat> findByShow_Id(Long showId);

}
