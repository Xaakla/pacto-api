package com.app.pactoapi.database.entities;

import com.app.pactoapi.enums.PaymentProcessorType;
import com.app.pactoapi.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.OffsetDateTime;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardToken;

    @NotBlank
    private String transactionId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentProcessorType paymentProcessor;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_id")
    private Sale sale;

    private OffsetDateTime createdAt = OffsetDateTime.now();

    @NotNull
    @Positive
    private Long amount = 0L;

    public Payment() {
    }

    public Payment(
            String cardToken,
            String transactionId,
            PaymentProcessorType paymentProcessor,
            PaymentStatus status,
            Sale sale,
            Long amount
    ) {
        this.cardToken = cardToken;
        this.transactionId = transactionId;
        this.paymentProcessor = paymentProcessor;
        this.status = status;
        this.sale = sale;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public PaymentProcessorType getPaymentProcessor() {
        return paymentProcessor;
    }

    public void setPaymentProcessor(PaymentProcessorType paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
