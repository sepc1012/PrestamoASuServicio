package com.prestamosasuservicio.backend.service;

import com.prestamosasuservicio.backend.entity.*;
import com.prestamosasuservicio.backend.enums.*;
import com.prestamosasuservicio.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final DailyCollectionRepository dailyCollectionRepository;
    private final CashBoxRepository cashBoxRepository;
    private final CapitalMovementRepository capitalMovementRepository;

    public LoanService(LoanRepository loanRepository,
                       DailyCollectionRepository dailyCollectionRepository,
                       CashBoxRepository cashBoxRepository,
                       CapitalMovementRepository capitalMovementRepository) {
        this.loanRepository = loanRepository;
        this.dailyCollectionRepository = dailyCollectionRepository;
        this.cashBoxRepository = cashBoxRepository;
        this.capitalMovementRepository = capitalMovementRepository;
    }

    @Transactional
    public Loan createLoan(Loan loan, PaymentMethod method) {
        CashBox box = cashBoxRepository.findByName(method)
                .orElseThrow(() -> new RuntimeException("Caja de " + method + " no encontrada"));

        if (box.getBalance().compareTo(loan.getAmount()) < 0) {
            throw new RuntimeException("Saldo insuficiente en " + method + ". Tienes: S/ " + box.getBalance());
        }

        BigDecimal capital = loan.getAmount();
        BigDecimal interesMensual = capital.multiply(loan.getInterestRate());

        BigDecimal factorTiempo = new BigDecimal(loan.getTotalCuotas())
                .divide(new BigDecimal(30), 4, RoundingMode.HALF_UP);

        BigDecimal gananciaTotal = interesMensual.multiply(factorTiempo).setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalDeuda = capital.add(gananciaTotal);

        loan.setInterestAmount(gananciaTotal);
        loan.setTotalToPay(totalDeuda);
        loan.setPendingAmount(totalDeuda);
        loan.setCuotasPagadas(0);
        loan.setStatus(LoanStatus.ACTIVE);

        calculateInstallments(loan, totalDeuda);

        box.setBalance(box.getBalance().subtract(capital));
        cashBoxRepository.save(box);

        CapitalMovement movement = new CapitalMovement();
        movement.setAmount(capital);
        movement.setType(MovementType.OUTCOME);
        movement.setDescription("Préstamo otorgado - Cliente ID: " + loan.getClient().getId());
        capitalMovementRepository.save(movement);

        Loan savedLoan = loanRepository.save(loan);
        generateDailyRoute(savedLoan);

        return savedLoan;
    }

    private void calculateInstallments(Loan loan, BigDecimal total) {
        int n = loan.getTotalCuotas();
        BigDecimal daily = total.divide(new BigDecimal(n), 0, RoundingMode.FLOOR);

        BigDecimal diasMenosUno = new BigDecimal(n - 1);
        while (daily.multiply(diasMenosUno).compareTo(total) >= 0 && daily.compareTo(BigDecimal.ONE) > 0) {
            daily = daily.subtract(BigDecimal.ONE);
        }

        loan.setDailyPayment(daily);
        loan.setFinalPayment(total.subtract(daily.multiply(diasMenosUno)));
    }

    private void generateDailyRoute(Loan loan) {
        List<DailyCollection> route = new ArrayList<>();
        LocalDate dateCursor = loan.getStartDate();
        int cuotaNro = 1;

        while (cuotaNro <= loan.getTotalCuotas()) {
            if (dateCursor.getDayOfWeek() == DayOfWeek.SUNDAY) {
                dateCursor = dateCursor.plusDays(1);
                continue;
            }

            DailyCollection dc = new DailyCollection();
            dc.setLoan(loan);
            dc.setCollectionDate(dateCursor);
            dc.setVisitOrder(cuotaNro);
            dc.setAmountToCollect(cuotaNro == loan.getTotalCuotas() ? loan.getFinalPayment() : loan.getDailyPayment());
            dc.setAmountCollected(BigDecimal.ZERO);
            dc.setStatus(CollectionStatus.PENDING);

            route.add(dc);
            if (cuotaNro == loan.getTotalCuotas()) loan.setEstimatedEndDate(dateCursor);

            dateCursor = dateCursor.plusDays(1);
            cuotaNro++;
        }
        dailyCollectionRepository.saveAll(route);
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }
}