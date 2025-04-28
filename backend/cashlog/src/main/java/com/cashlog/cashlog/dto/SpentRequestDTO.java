package com.cashlog.cashlog.dto;

import com.cashlog.cashlog.entities.ScheduleSpent;
import com.cashlog.cashlog.enums.ExpenseType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SpentRequestDTO {
    
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotNull(message = "Amount is required")
    private Long amount;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @NotNull(message = "Monetary Fund is required")
    private Long monetaryFundId;

    private ScheduleSpent scheduleSpent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public Long getMonetaryFundId() {
        return monetaryFundId;
    }

    public void setMonetaryFundId(Long monetaryFundId) {
        this.monetaryFundId = monetaryFundId;
    }

    public ScheduleSpent getScheduleSpent() {
        return scheduleSpent;
    }

    public void setScheduleSpent(ScheduleSpent scheduleSpent) {
        this.scheduleSpent = scheduleSpent;
    }
}
