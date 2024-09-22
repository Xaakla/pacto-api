package com.app.pactoapi.dtos.sale;

import com.app.pactoapi.database.entities.Sale;
import com.app.pactoapi.enums.SalePaymentStatus;

public class SaleListDto {

    private Long id;
    private String description;
    private Long amount;
    private String currency;
    private SalePaymentStatus paymentStatus;

    public SaleListDto() {
    }

    public SaleListDto(Long id, String description, Long amount, String currency, SalePaymentStatus paymentStatus) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.paymentStatus = paymentStatus;
    }

    public SaleListDto(Sale sale) {
        this.id = sale.getId();
        this.description = sale.getDescription();
        this.amount = sale.getAmount();
        this.currency = sale.getCurrency();
        this.paymentStatus = sale.getPaymentStatus();
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

    public SalePaymentStatus getStatus() {
        return paymentStatus;
    }

    public void setStatus(SalePaymentStatus status) {
        this.paymentStatus = status;
    }
}