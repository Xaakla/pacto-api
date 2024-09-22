package com.app.pactoapi.paymentprocessing.cielo.dtos.response;

import com.app.pactoapi.paymentprocessing.cielo.dtos.CieloTransactionalStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CieloSalesPaymentResponseDto {

    @JsonProperty("PaymentId")
    private String paymentId;

    @JsonProperty("ReturnMessage")
    private String returnMessage;

    @JsonProperty("ReturnCode")
    private String returnCode;


    @JsonProperty("Amount")
    private Long amount;

    @JsonProperty("Status")
    private CieloTransactionalStatus status;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public CieloTransactionalStatus getStatus() {
        return status;
    }

    public void setStatus(CieloTransactionalStatus status) {
        this.status = status;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
