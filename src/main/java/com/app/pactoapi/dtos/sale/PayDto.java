package com.app.pactoapi.dtos.sale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PayDto {

    @NotNull
    private UUID transactionId;

    @NotBlank
    private String securityCode;

    public PayDto() {

    }

    public PayDto(UUID transactionId, String securityCode) {
        this.transactionId = transactionId;
        this.securityCode = securityCode;
    }

    public UUID getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(UUID transactionId) {
        this.transactionId = transactionId;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
}
