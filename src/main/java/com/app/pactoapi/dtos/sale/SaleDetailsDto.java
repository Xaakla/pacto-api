package com.app.pactoapi.dtos.sale;

import com.app.pactoapi.database.entities.Sale;

public class SaleDetailsDto {

    private Long id;
    private String description;
    private Long amount;
    private String currency;

    public SaleDetailsDto() {
    }

    public SaleDetailsDto(Long id, String description, Long amount, String currency) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.currency = currency;
    }

    public SaleDetailsDto(Sale sale) {
        this.id = sale.getId();
        this.description = sale.getDescription();
        this.amount = sale.getAmount();
        this.currency = sale.getCurrency();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}