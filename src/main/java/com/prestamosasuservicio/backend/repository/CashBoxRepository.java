package com.prestamosasuservicio.backend.repository;

import com.prestamosasuservicio.backend.entity.CapitalMovement;
import com.prestamosasuservicio.backend.entity.CashBox;
import com.prestamosasuservicio.backend.enums.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface CashBoxRepository extends JpaRepository<CashBox, Long> {
    Optional<CashBox> findByName(PaymentMethod name);
}

