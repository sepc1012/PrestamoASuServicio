package com.prestamosasuservicio.backend.controller;

import com.prestamosasuservicio.backend.entity.CashBox;
import com.prestamosasuservicio.backend.service.CapitalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/capital")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class CapitalController {

    private final CapitalService capitalService;

    public CapitalController(CapitalService capitalService) {
        this.capitalService = capitalService;
    }

    @GetMapping("/balances")
    public ResponseEntity<List<CashBox>> getBalances() {
        return ResponseEntity.ok(capitalService.getAllBalances());
    }
}