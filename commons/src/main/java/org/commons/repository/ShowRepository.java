package org.commons.repository;

import org.commons.entity.MovieShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<MovieShow, Long> {

    @Query("SELECT s FROM MovieShow s " +
            "JOIN s.movie m " +
            "JOIN s.screen sc " +
            "JOIN sc.theatre t " +
            "WHERE m.id = :movieId " +
            "AND t.city = :city " +
            "AND s.startTime >= :start " +
            "AND s.startTime <= :end " +
            "AND s.status = 'ACTIVE'")
    List<MovieShow> findShowsByFilters(
            @Param("movieId") Long movieId,
            @Param("city") String city,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    /**
     * overlaps with any existing [StartTime, EndTime].
     * Formula: (RequestedStart < ExistingEnd) AND (RequestedEnd > ExistingStart)
     */
    @Query("SELECT COUNT(s) > 0 FROM MovieShow s " +
            "WHERE s.screen.id = :screenId " +
            "AND (:start < s.endTime AND :end > s.startTime) " +
            "AND s.status != 'CANCELLED'")
    boolean existsOverlap(
            @Param("screenId") Long screenId,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

}
