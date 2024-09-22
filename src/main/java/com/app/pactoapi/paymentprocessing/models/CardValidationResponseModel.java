package com.app.pactoapi.paymentprocessing.models;

public class CardValidationResponseModel {

    private boolean valid;

    public CardValidationResponseModel(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
