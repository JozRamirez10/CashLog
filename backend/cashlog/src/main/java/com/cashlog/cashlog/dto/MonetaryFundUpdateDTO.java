package com.cashlog.cashlog.dto;

import com.cashlog.cashlog.enums.FundsCategory;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

public class MonetaryFundUpdateDTO {

    @NotBlank(message = "Name is required")
    private String name;
    
    private String number;
    private String description;

    @Enumerated(EnumType.STRING)
    private FundsCategory fundsCategory;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FundsCategory getFundsCategory() {
        return fundsCategory;
    }

    public void setFundsCategory(FundsCategory fundsCategory) {
        this.fundsCategory = fundsCategory;
    }   
}
