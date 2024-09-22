package com.app.pactoapi.enums;

public enum PaymentStatus {
    SUCCESS(true),
    PAYMENT_PENDING,
    FAILED,
    REFUNDED;

    public final boolean refundable;

    PaymentStatus() {
        this.refundable = false;
    }

    PaymentStatus(boolean refundable) {
        this.refundable = refundable;
    }

    public boolean isRefundable() {
        return refundable;
    }

}
