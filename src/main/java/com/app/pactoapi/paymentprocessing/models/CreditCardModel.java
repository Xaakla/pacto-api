package com.app.pactoapi.paymentprocessing.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreditCardModel {

    @NotNull
    private String cardNumber;

    @NotBlank
    private String holder;

    @NotNull
    private String expirationDate;

    @NotBlank
    private String securityCode;

    @NotBlank
    private String brand;

    @NotNull
    private CardTypeModel cardType;

    private boolean saveCard;

    public CreditCardModel(String cardNumber, String holder, String expirationDate, String securityCode, String brand, CardTypeModel cardType) {
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

    public CardTypeModel getCardType() {
        return cardType;
    }

    public void setCardType(CardTypeModel cardType) {
        this.cardType = cardType;
    }

    public boolean isSaveCard() {
        return saveCard;
    }

    public void setSaveCard(boolean saveCard) {
        this.saveCard = saveCard;
    }
}
