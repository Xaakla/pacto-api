package com.app.pactoapi.paymentprocessing.cielo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.app.pactoapi.enums.PaymentStatus;

public enum CieloTransactionalStatus {

    @JsonProperty("0")
    NOT_FINISHED(PaymentStatus.PAYMENT_PENDING),

    @JsonProperty("1")
    AUTHORIZED(PaymentStatus.PAYMENT_PENDING),

    @JsonProperty("2")
    PAYMENT_CONFIRMED(PaymentStatus.SUCCESS),

    @JsonProperty("3")
    DENIED(PaymentStatus.FAILED),

    @JsonProperty("10")
    VOIDED(PaymentStatus.FAILED),

    @JsonProperty("11")
    REFUNDED(PaymentStatus.REFUNDED),

    @JsonProperty("12")
    PENDING(PaymentStatus.PAYMENT_PENDING),

    @JsonProperty("13")
    ABORTED(PaymentStatus.FAILED),

    @JsonProperty("20")
    SCHEDULED(PaymentStatus.PAYMENT_PENDING);

    public final PaymentStatus paymentStatus;

    CieloTransactionalStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

}
