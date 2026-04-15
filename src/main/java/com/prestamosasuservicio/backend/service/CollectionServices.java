package com.prestamosasuservicio.backend.service;

import com.prestamosasuservicio.backend.entity.DailyCollection;
import com.prestamosasuservicio.backend.enums.CollectionStatus;
import com.prestamosasuservicio.backend.repository.DailyCollectionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class CollectionServices {

    private final DailyCollectionRepository collectionRepository;

    public CollectionServices(DailyCollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    public List<DailyCollection> getMainRoute(LocalDate date) {
        return collectionRepository.findRouteByDate(date);
    }

    public List<DailyCollection> getLoanHistory(Long loanId) {
        return collectionRepository.findByLoanIdOrderByVisitOrderAsc(loanId);
    }

    @Transactional
    public DailyCollection updateCollectionStatus(Long id, CollectionStatus status, String notes) {
        DailyCollection dc = collectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuota no encontrada con ID: " + id));

        if (status != null) dc.setStatus(status);
        if (notes != null) dc.setNotes(notes);

        return collectionRepository.save(dc);
    }
}