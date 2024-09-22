package com.app.pactoapi.paymentprocessing.models;

import com.app.pactoapi.enums.PaymentStatus;

public class CreditCardPaymentResponseModel {

    private String cardToken;
    private PaymentStatus paymentStatus;
    private String transactionId;
    private long amount;

    public CreditCardPaymentResponseModel(String cardToken, PaymentStatus paymentStatus, String transactionId, long amount) {
        this.cardToken = cardToken;
        this.paymentStatus = paymentStatus;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public String getCardToken() {
        return cardToken;
    }

    public void setCardToken(String cardToken) {
        this.cardToken = cardToken;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
