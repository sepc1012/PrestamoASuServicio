package com.prestamosasuservicio.backend.controller;

import com.prestamosasuservicio.backend.entity.Payment;
import com.prestamosasuservicio.backend.enums.PaymentMethod;
import com.prestamosasuservicio.backend.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/payments")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/collect/{dailyCollectionId}")
    public ResponseEntity<?> processPayment(
            @PathVariable Long dailyCollectionId,
            @RequestBody Map<String, Object> payload) {

        try {

            BigDecimal amount = new BigDecimal(payload.get("amount").toString());
            PaymentMethod method = PaymentMethod.valueOf(payload.get("method").toString());
            String registeredBy = payload.get("registeredBy").toString();

            Payment payment = paymentService.processPayment(dailyCollectionId, amount, method, registeredBy);

            return new ResponseEntity<>(payment, HttpStatus.CREATED);

        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Error al procesar pago: " + e.getMessage());
        }
    }
}