package com.prestamosasuservicio.backend.controller;

import com.prestamosasuservicio.backend.entity.CashBox;
import com.prestamosasuservicio.backend.service.CashBoxService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cashbox")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class CashBoxController {

    private final CashBoxService cashBoxService;

    public CashBoxController(CashBoxService cashBoxService) {
        this.cashBoxService = cashBoxService;
    }

    @GetMapping("/balances")
    public ResponseEntity<List<CashBox>> getBalances() {
        return ResponseEntity.ok(cashBoxService.findAll());
    }
}