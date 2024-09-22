package com.app.pactoapi.paymentprocessing.cielo.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CieloSalesCustomerRequestDto {

    @JsonProperty("Name")
    private String name;

    public CieloSalesCustomerRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
