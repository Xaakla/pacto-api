package com.app.pactoapi.paymentprocessing.cielo.dtos.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CieloSalesResponseDto {

    @JsonProperty("MerchantOrderId")
    private String merchantOrderId;

    @JsonProperty("Payment")
    private CieloSalesPaymentResponseDto payment;

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public CieloSalesPaymentResponseDto getPayment() {
        return payment;
    }

    public void setPayment(CieloSalesPaymentResponseDto payment) {
        this.payment = payment;
    }
}
