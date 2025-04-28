package com.cashlog.cashlog.entities;

import com.cashlog.cashlog.enums.ExpenseType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Spent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;
    private String description;

    @NotNull(message = "Amount is required")
    private Long amount;

    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "monetary_fund_id", nullable = false)
    @JsonIgnore
    private MonetaryFund monetaryFund;

    @OneToOne(
        fetch = FetchType.LAZY,
        mappedBy = "spent",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    // @JsonIgnore
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
    public MonetaryFund getMonetaryFund() {
        return monetaryFund;
    }
    public void setMonetaryFund(MonetaryFund monetaryFund) {
        this.monetaryFund = monetaryFund;
    }
    public ExpenseType getExpenseType() {
        return expenseType;
    }
    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }
    public ScheduleSpent getScheduleSpent() {
        return scheduleSpent;
    }
    public void setScheduleSpent(ScheduleSpent scheduleSpent) {
        this.scheduleSpent = scheduleSpent;
    }
}
