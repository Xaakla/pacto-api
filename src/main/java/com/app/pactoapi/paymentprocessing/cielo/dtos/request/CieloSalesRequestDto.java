package com.app.pactoapi.paymentprocessing.cielo.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CieloSalesRequestDto {

    @JsonProperty("MerchantOrderId")
    private String merchantOrderId;

    @JsonProperty("Customer")
    private CieloSalesCustomerRequestDto customer;

    @JsonProperty("Payment")
    private CieloSalesPaymentRequestDto payment;

    public CieloSalesRequestDto(String merchantOrderId,
                                CieloSalesCustomerRequestDto customer,
                                CieloSalesPaymentRequestDto payment) {
        this.merchantOrderId = merchantOrderId;
        this.payment = payment;
    }

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public CieloSalesCustomerRequestDto getCustomer() {
        return customer;
    }

    public void setCustomer(CieloSalesCustomerRequestDto customer) {
        this.customer = customer;
    }

    public CieloSalesPaymentRequestDto getPayment() {
        return payment;
    }

    public  void setPayment(CieloSalesPaymentRequestDto payment) {
        this.payment = payment;
    }
}
