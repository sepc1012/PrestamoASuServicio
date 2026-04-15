package com.prestamosasuservicio.backend.service;

import com.prestamosasuservicio.backend.entity.*;
import com.prestamosasuservicio.backend.enums.*;
import com.prestamosasuservicio.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final DailyCollectionRepository dailyCollectionRepository;
    private final LoanRepository loanRepository;
    private final CashBoxRepository cashBoxRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          DailyCollectionRepository dailyCollectionRepository,
                          LoanRepository loanRepository,
                          CashBoxRepository cashBoxRepository) {
        this.paymentRepository = paymentRepository;
        this.dailyCollectionRepository = dailyCollectionRepository;
        this.loanRepository = loanRepository;
        this.cashBoxRepository = cashBoxRepository;
    }

    @Transactional
    public Payment processPayment(Long dailyCollectionId, BigDecimal amount, PaymentMethod method, String registeredBy) {

        DailyCollection dc = dailyCollectionRepository.findById(dailyCollectionId)
                .orElseThrow(() -> new RuntimeException("Cobro no encontrado"));

        Loan loan = dc.getLoan();

        Payment payment = new Payment();
        payment.setDailyCollection(dc);
        payment.setLoan(loan);
        payment.setAmountPaid(amount);
        payment.setPaymentMethod(method);
        payment.setRegisteredBy(registeredBy);
        Payment savedPayment = paymentRepository.save(payment);

        BigDecimal newAmountCollected = dc.getAmountCollected().add(amount);
        dc.setAmountCollected(newAmountCollected);

        if (newAmountCollected.compareTo(dc.getAmountToCollect()) >= 0) {
            dc.setStatus(CollectionStatus.PAID);
        } else {
            dc.setStatus(CollectionStatus.PARTIAL);
        }
        dailyCollectionRepository.save(dc);

        loan.setPendingAmount(loan.getPendingAmount().subtract(amount));

        if (loan.getPendingAmount().compareTo(BigDecimal.ZERO) <= 0) {
            loan.setStatus(LoanStatus.COMPLETED);
            loan.setActualEndDate(java.time.LocalDate.now());
        }
        loanRepository.save(loan);

        CashBox box = cashBoxRepository.findByName(method)
                .orElseThrow(() -> new RuntimeException("Caja no encontrada"));
        box.setBalance(box.getBalance().add(amount));
        cashBoxRepository.save(box);

        return savedPayment;
    }
}