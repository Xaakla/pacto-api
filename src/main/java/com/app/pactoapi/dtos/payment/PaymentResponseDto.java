package com.app.pactoapi.dtos.payment;

import com.app.pactoapi.database.entities.Payment;
import com.app.pactoapi.enums.PaymentProcessorType;
import com.app.pactoapi.enums.PaymentStatus;

import java.time.OffsetDateTime;

public class PaymentResponseDto {
    private Long id;
    private String transactionId;
    private PaymentProcessorType paymentProcessor;
    private PaymentStatus status;
    private OffsetDateTime createdAt;
    private Long amount;

    public PaymentResponseDto(Long id, String transactionId, PaymentProcessorType paymentProcessor, PaymentStatus status, OffsetDateTime createdAt, Long amount) {
        this.id = id;
        this.transactionId = transactionId;
        this.paymentProcessor = paymentProcessor;
        this.status = status;
        this.createdAt = createdAt;
        this.amount = amount;
    }

    public PaymentResponseDto(Payment payment) {
        this.id = payment.getId();
        this.transactionId = payment.getTransactionId();
        this.paymentProcessor = payment.getPaymentProcessor();
        this.status = payment.getStatus();
        this.createdAt = payment.getCreatedAt();
        this.amount = payment.getAmount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
