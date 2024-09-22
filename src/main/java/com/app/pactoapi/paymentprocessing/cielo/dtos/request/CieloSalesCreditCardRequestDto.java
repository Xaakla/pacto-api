package com.app.pactoapi.paymentprocessing.cielo.dtos.request;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CieloSalesCreditCardRequestDto {

    @JsonProperty("CardNumber")
    private String cardNumber;

    @JsonProperty("Holder")
    private String holder;

    @JsonProperty("ExpirationDate")
    private String expirationDate;

    @JsonProperty("SecurityCode")
    private String securityCode;

    @JsonProperty("Brand")
    private String brand;

    public CieloSalesCreditCardRequestDto(String cardNumber, String holder, String expirationDate, String securityCode, String brand) {
        this.cardNumber = cardNumber;
        this.holder = holder;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.brand = brand;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
