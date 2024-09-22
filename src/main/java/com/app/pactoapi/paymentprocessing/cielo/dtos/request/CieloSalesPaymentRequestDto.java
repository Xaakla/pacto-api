package com.app.pactoapi.paymentprocessing.cielo.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CieloSalesPaymentRequestDto {

    @JsonProperty("Amount")
    private long amount;

    @JsonProperty("CreditCard")
    private CieloSalesCreditCardRequestDto creditCard;

    public CieloSalesPaymentRequestDto(long amount, CieloSalesCreditCardRequestDto creditCard) {
        this.amount = amount;
        this.creditCard = creditCard;
    }

    @JsonProperty("Type")
    public String getType() {
        return "CreditCard";
    }

    @JsonProperty("Installments")
    public int getInstallments() {
        return 1;
    }

    @JsonProperty("Capture")
    public boolean isCapture() {
        return true;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
