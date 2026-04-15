package com.prestamosasuservicio.backend.controller;

import com.prestamosasuservicio.backend.entity.Loan;
import com.prestamosasuservicio.backend.enums.PaymentMethod;
import com.prestamosasuservicio.backend.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }
    @PostMapping
    public ResponseEntity<?> createLoan(
            @RequestBody Loan loan,
            @RequestParam PaymentMethod method) {
        try {
            Loan createdLoan = loanService.createLoan(loan, method);
            return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanService.findAll());
    }
}