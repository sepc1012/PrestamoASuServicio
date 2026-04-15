package com.prestamosasuservicio.backend.entity;

import com.prestamosasuservicio.backend.enums.LoanStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Dinero neto prestado (Capital inicial)
    private BigDecimal amount;

    // Tasa de interés (Ej: 0.20 para el 20%)
    private BigDecimal interestRate;

    // Ganancia en dinero (Ej: S/ 200)
    private BigDecimal interestAmount;

    // La suma de amount + interestAmount (Ej: S/ 1200)
    private BigDecimal totalToPay;

    // Lo que falta cobrar de los 1200
    private BigDecimal pendingAmount;

    private BigDecimal dailyPayment;
    private BigDecimal finalPayment;
    private Integer totalCuotas;
    private Integer cuotasPagadas;

    private LocalDate startDate;
    private LocalDate estimatedEndDate;
    private LocalDate actualEndDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.ACTIVE;

    private LocalDateTime createdAt;

    public Loan() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.pendingAmount == null) {
            this.pendingAmount = this.totalToPay;
        }
        if (this.cuotasPagadas == null) {
            this.cuotasPagadas = 0;
        }
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getInterestRate() { return interestRate; }
    public void setInterestRate(BigDecimal interestRate) { this.interestRate = interestRate; }

    public BigDecimal getInterestAmount() { return interestAmount; }
    public void setInterestAmount(BigDecimal interestAmount) { this.interestAmount = interestAmount; }

    public BigDecimal getTotalToPay() { return totalToPay; }
    public void setTotalToPay(BigDecimal totalToPay) { this.totalToPay = totalToPay; }

    public BigDecimal getPendingAmount() { return pendingAmount; }
    public void setPendingAmount(BigDecimal pendingAmount) { this.pendingAmount = pendingAmount; }

    public BigDecimal getDailyPayment() { return dailyPayment; }
    public void setDailyPayment(BigDecimal dailyPayment) { this.dailyPayment = dailyPayment; }

    public BigDecimal getFinalPayment() { return finalPayment; }
    public void setFinalPayment(BigDecimal finalPayment) { this.finalPayment = finalPayment; }

    public Integer getTotalCuotas() { return totalCuotas; }
    public void setTotalCuotas(Integer totalCuotas) { this.totalCuotas = totalCuotas; }

    public Integer getCuotasPagadas() { return cuotasPagadas; }
    public void setCuotasPagadas(Integer cuotasPagadas) { this.cuotasPagadas = cuotasPagadas; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEstimatedEndDate() { return estimatedEndDate; }
    public void setEstimatedEndDate(LocalDate estimatedEndDate) { this.estimatedEndDate = estimatedEndDate; }

    public LocalDate getActualEndDate() { return actualEndDate; }
    public void setActualEndDate(LocalDate actualEndDate) { this.actualEndDate = actualEndDate; }

    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}