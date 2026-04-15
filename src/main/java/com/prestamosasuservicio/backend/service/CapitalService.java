package com.prestamosasuservicio.backend.service;

import com.prestamosasuservicio.backend.entity.CapitalMovement;
import com.prestamosasuservicio.backend.enums.MovementType;
import com.prestamosasuservicio.backend.enums.PaymentMethod;
import com.prestamosasuservicio.backend.repository.CapitalMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.prestamosasuservicio.backend.entity.CashBox;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CapitalService {

    private final CapitalMovementRepository movementRepository;
    private final CashBoxService cashBoxService;

    public CapitalService(CapitalMovementRepository movementRepository, CashBoxService cashBoxService) {
        this.movementRepository = movementRepository;
        this.cashBoxService = cashBoxService;
    }

    @Transactional
    public void registerManualMovement(BigDecimal amount, MovementType type, PaymentMethod method, String desc) {

        boolean isIncrease = (type == MovementType.INCOME);
        cashBoxService.updateBalance(method, amount, isIncrease);

        CapitalMovement movement = new CapitalMovement();
        movement.setAmount(amount);
        movement.setType(type);
        movement.setDescription(desc + " (Vía " + method + ")");

        movementRepository.save(movement);
    }
    public List<CashBox> getAllBalances() {
        return cashBoxService.findAll();
    }
}