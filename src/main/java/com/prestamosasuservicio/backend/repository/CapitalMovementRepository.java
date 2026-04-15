package com.prestamosasuservicio.backend.repository;

import com.prestamosasuservicio.backend.entity.CapitalMovement;
import com.prestamosasuservicio.backend.enums.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CapitalMovementRepository extends JpaRepository<CapitalMovement, Long> {

    java.util.List<CapitalMovement> findByActorId(Long userId);
}