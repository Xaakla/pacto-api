package com.app.pactoapi.dtos.payment;

import com.app.pactoapi.paymentprocessing.models.CreditCardModel;
import jakarta.validation.constraints.NotNull;

public class NewPaymentDto {
    @NotNull
    private CreditCardModel creditCard;

    @NotNull
    private Long saleId;

    @NotNull
    private Long amount;

    public NewPaymentDto() {
    }

    public CreditCardModel getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCardModel creditCard) {
        this.creditCard = creditCard;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
