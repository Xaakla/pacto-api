package com.app.pactoapi.paymentprocessing.dtos;

public class CardValidationResponseDto {

    private boolean valid;

    public CardValidationResponseDto(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
