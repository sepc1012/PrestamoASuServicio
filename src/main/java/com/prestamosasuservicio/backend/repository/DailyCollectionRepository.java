package com.prestamosasuservicio.backend.repository;

import com.prestamosasuservicio.backend.entity.DailyCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DailyCollectionRepository extends JpaRepository<DailyCollection, Long> {

    @Query("SELECT d FROM DailyCollection d JOIN FETCH d.loan l JOIN FETCH l.client " +
            "WHERE d.collectionDate = :fecha ORDER BY d.visitOrder ASC")
    List<DailyCollection> findRouteByDate(@Param("fecha") LocalDate fecha);

    List<DailyCollection> findByLoanIdAndStatus(Long loanId, String status);

    List<DailyCollection> findByCollectionDate(LocalDate date);

    List<DailyCollection> findByLoanIdOrderByVisitOrderAsc(Long loanId);
}
