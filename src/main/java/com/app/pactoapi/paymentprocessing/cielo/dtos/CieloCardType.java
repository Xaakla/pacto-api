package com.app.pactoapi.paymentprocessing.cielo.dtos;

import com.app.pactoapi.paymentprocessing.models.CardTypeModel;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum CieloCardType {

    @JsonProperty("CreditCard")
    CREDIT(CardTypeModel.CREDIT),

    @JsonProperty("DebitCard")
    DEBIT(CardTypeModel.DEBIT);

    public final CardTypeModel model;

    CieloCardType(CardTypeModel model) {
        this.model = model;
    }

    public static CieloCardType fromModel(CardTypeModel model) {
        for (CieloCardType cardType : CieloCardType.values()) {
            if (cardType.model == model) {
                return cardType;
            }
        }

        return null;
    }
}
