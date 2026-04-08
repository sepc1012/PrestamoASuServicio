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

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private BigDecimal amount;
    private BigDecimal interestRate;
    private BigDecimal totalToPay;
    private BigDecimal dailyPayment;

    private Integer totalCuotas;
    private LocalDate startDate;
    private LocalDate estimatedEndDate;
    private LocalDate actualEndDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.ACTIVE;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Loan(Long id, Client client, BigDecimal amount, BigDecimal interestRate, BigDecimal totalToPay, BigDecimal dailyPayment, Integer totalCuotas, LocalDate startDate, LocalDate estimatedEndDate, LocalDate actualEndDate, LoanStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.client = client;
        this.amount = amount;
        this.interestRate = interestRate;
        this.totalToPay = totalToPay;
        this.dailyPayment = dailyPayment;
        this.totalCuotas = totalCuotas;
        this.startDate = startDate;
        this.estimatedEndDate = estimatedEndDate;
        this.actualEndDate = actualEndDate;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public Client getClient() {return client;}
    public void setClient(Client client) {this.client = client;}
    public BigDecimal getAmount() {return amount;}
    public void setAmount(BigDecimal amount) {this.amount = amount;}
    public BigDecimal getInterestRate() {return interestRate;}
    public void setInterestRate(BigDecimal interestRate) {this.interestRate = interestRate;}
    public BigDecimal getTotalToPay() {return totalToPay;}
    public void setTotalToPay(BigDecimal totalToPay) {this.totalToPay = totalToPay;}
    public BigDecimal getDailyPayment() {return dailyPayment;}
    public void setDailyPayment(BigDecimal dailyPayment) {this.dailyPayment = dailyPayment;}
    public Integer getTotalCuotas() {return totalCuotas;}
    public void setTotalCuotas(Integer totalCuotas) {this.totalCuotas = totalCuotas;}
    public LocalDate getStartDate() {return startDate;}
    public void setStartDate(LocalDate startDate) {this.startDate = startDate;}
    public LocalDate getEstimatedEndDate() {return estimatedEndDate;}
    public void setEstimatedEndDate(LocalDate estimatedEndDate) {this.estimatedEndDate = estimatedEndDate;}
    public LocalDate getActualEndDate() {return actualEndDate;}
    public void setActualEndDate(LocalDate actualEndDate) {this.actualEndDate = actualEndDate;}
    public LoanStatus getStatus() {return status;}
    public void setStatus(LoanStatus status) {this.status = status;}
    public LocalDateTime getCreatedAt() {return createdAt;}
    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt;}
}