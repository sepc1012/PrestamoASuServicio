package com.prestamosasuservicio.backend.service;

import com.prestamosasuservicio.backend.entity.CashBox;
import com.prestamosasuservicio.backend.enums.PaymentMethod;
import com.prestamosasuservicio.backend.repository.CashBoxRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CashBoxService {

    private final CashBoxRepository cashBoxRepository;

    public CashBoxService(CashBoxRepository cashBoxRepository) {
        this.cashBoxRepository = cashBoxRepository;
    }

    public List<CashBox> findAll() {
        return cashBoxRepository.findAll();
    }

    @Transactional
    public void updateBalance(PaymentMethod method, BigDecimal amount, boolean isIncrease) {
        CashBox box = cashBoxRepository.findByName(method)
                .orElseThrow(() -> new RuntimeException("Caja " + method + " no inicializada"));

        if (!isIncrease && box.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo insuficiente en " + method);
        }

        if (isIncrease) {
            box.setBalance(box.getBalance().add(amount));
        } else {
            box.setBalance(box.getBalance().subtract(amount));
        }

        cashBoxRepository.save(box);
    }

    public CashBox getByName(PaymentMethod method) {
        return cashBoxRepository.findByName(method)
                .orElseThrow(() -> new RuntimeException("Caja no encontrada"));
    }
}