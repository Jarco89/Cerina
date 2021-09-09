package nl.schutrup.cerina.abac.repository;

import nl.schutrup.cerina.abac.entities.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

   Optional<Statistic> findFirstByOrderByVisitDateTimeDesc();


   @Query("select m from Statistic m " +
           "where m.visitDateTime >= :startDate and m.visitDateTime < :endDate")
   public List<Statistic> findAllByVisitDateTimeBetween(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate);
}
