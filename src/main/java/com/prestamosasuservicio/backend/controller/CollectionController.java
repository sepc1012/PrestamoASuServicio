package com.prestamosasuservicio.backend.controller;

import com.prestamosasuservicio.backend.entity.DailyCollection;
import com.prestamosasuservicio.backend.enums.CollectionStatus;
import com.prestamosasuservicio.backend.service.CollectionServices;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collections")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class CollectionController {

    private final CollectionServices collectionServices;

    public CollectionController(CollectionServices collectionServices) {
        this.collectionServices = collectionServices;
    }


    @GetMapping("/daily-route")
    public ResponseEntity<List<DailyCollection>> getRoute(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        return ResponseEntity.ok(collectionServices.getMainRoute(targetDate));
    }


    @GetMapping("/loan/{loanId}")
    public ResponseEntity<List<DailyCollection>> getByLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(collectionServices.getLoanHistory(loanId));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<DailyCollection> patchCollection(
            @PathVariable Long id,
            @RequestParam(required = false) CollectionStatus status,
            @RequestParam(required = false) String notes) {

        return ResponseEntity.ok(collectionServices.updateCollectionStatus(id, status, notes));
    }
}