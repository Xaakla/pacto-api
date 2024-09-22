package com.app.pactoapi.paymentprocessing.cielo.dtos.request;

import com.app.pactoapi.paymentprocessing.cielo.dtos.CieloCardType;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CieloCardValidationRequestDto {

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

    @JsonProperty("CardType")
    private CieloCardType cardType;

    @JsonProperty("SaveCard")
    private boolean saveCard;

    public CieloCardValidationRequestDto(String cardNumber, String holder, String expirationDate, String securityCode, String brand, CieloCardType cardType) {
        this.cardNumber = cardNumber;
        this.holder = holder;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.brand = brand;
        this.cardType = cardType;
        this.saveCard = false;
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

    public CieloCardType getCardType() {
        return cardType;
    }

    public void setCardType(CieloCardType cardType) {
        this.cardType = cardType;
    }

    public boolean isSaveCard() {
        return saveCard;
    }

    public void setSaveCard(boolean saveCard) {
        this.saveCard = saveCard;
    }
}
