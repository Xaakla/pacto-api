package com.app.pactoapi.paymentprocessing.models;

public class PaymentCancelationResponseModel {

    private boolean successful;

    public PaymentCancelationResponseModel(boolean successful) {
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
