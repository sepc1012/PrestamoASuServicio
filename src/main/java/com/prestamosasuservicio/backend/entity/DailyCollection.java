package com.prestamosasuservicio.backend.entity;

import com.prestamosasuservicio.backend.enums.CollectionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_collections")
public class DailyCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id", nullable = false)
    private Loan loan;

    private LocalDate collectionDate;
    private BigDecimal amountToCollect;
    private BigDecimal amountCollected;

    private Integer visitOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CollectionStatus status;

    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DailyCollection() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.amountCollected == null) {
            this.amountCollected = BigDecimal.ZERO;
        }
        if (this.status == null) {
            this.status = CollectionStatus.PENDING;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }

    public LocalDate getCollectionDate() { return collectionDate; }
    public void setCollectionDate(LocalDate collectionDate) { this.collectionDate = collectionDate; }

    public BigDecimal getAmountToCollect() { return amountToCollect; }
    public void setAmountToCollect(BigDecimal amountToCollect) { this.amountToCollect = amountToCollect; }

    public BigDecimal getAmountCollected() { return amountCollected; }
    public void setAmountCollected(BigDecimal amountCollected) { this.amountCollected = amountCollected; }

    public Integer getVisitOrder() { return visitOrder; }
    public void setVisitOrder(Integer visitOrder) { this.visitOrder = visitOrder; }

    public CollectionStatus getStatus() { return status; }
    public void setStatus(CollectionStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}