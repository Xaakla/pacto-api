package com.app.pactoapi.database.entities;

import com.app.pactoapi.enums.PaymentStatus;
import com.app.pactoapi.enums.SalePaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String description;

    @NotNull
    @Positive
    private Long amount;

    @NotBlank
    private String currency;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Payment> payments;

    private OffsetDateTime createdAt = OffsetDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Sale() {
    }

    public Sale(String description, Long amount, String currency, User user) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Transient
    public SalePaymentStatus getPaymentStatus() {
        if (payments == null || payments.isEmpty())
            return SalePaymentStatus.PENDING;

        var totalPaid = payments.stream()
                .filter(it -> it.getStatus() == PaymentStatus.SUCCESS)
                .mapToLong(Payment::getAmount).sum();

        if (totalPaid == 0)
            return SalePaymentStatus.PENDING;

        if (totalPaid > amount)
            return SalePaymentStatus.OVERPAID;

        if (totalPaid < amount)
            return SalePaymentStatus.PARTIALLY_PAID;

        return SalePaymentStatus.FULLY_PAID;
    }
}
