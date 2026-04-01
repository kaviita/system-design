package org.commons.repository;

import org.commons.entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    // Used during show cancellation to update all seats at once
    List<ShowSeat> findByShow_Id(Long showId);

}
