package com.app.pactoapi.paymentprocessing.dtos;

public class PaymentCancelationResponseDto {

    private boolean successful;

    public PaymentCancelationResponseDto(boolean successful) {
        this.successful = successful;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public boolean isFailed() {
        return !isSuccessful();
    }
}
