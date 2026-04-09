package com.prestamosasuservicio.backend.repository;

import com.prestamosasuservicio.backend.entity.Loan;
import com.prestamosasuservicio.backend.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LoanRepository extends JpaRepository<Loan,Long> {
    List<Loan> findByStatus(LoanStatus status);

    List<Loan> findByClientId(Long clientId);
}
