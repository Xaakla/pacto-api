package com.app.pactoapi.dtos.sale;

import com.app.pactoapi.database.entities.Sale;
import com.app.pactoapi.dtos.payment.PaymentResponseDto;
import com.app.pactoapi.enums.SalePaymentStatus;

import java.util.List;

public class SaleDetailsDto {

    private Long id;
    private String description;
    private Long amount;
    private String currency;
    private SalePaymentStatus status;
    private List<PaymentResponseDto> payments;

    public SaleDetailsDto(Sale sale) {
        this.id= sale.getId();
        this.description = sale.getDescription();
        this.amount = sale.getAmount();
        this.currency = sale.getCurrency();
        this.status = sale.getPaymentStatus();
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

    public List<PaymentResponseDto> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentResponseDto> payments) {
        this.payments = payments;
    }

    public SalePaymentStatus getStatus() {
        return status;
    }

    public void setStatus(SalePaymentStatus status) {
        this.status = status;
    }
}
