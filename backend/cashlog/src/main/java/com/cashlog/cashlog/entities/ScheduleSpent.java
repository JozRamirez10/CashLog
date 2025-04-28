package com.cashlog.cashlog.entities;

import java.time.LocalDate;

import com.cashlog.cashlog.enums.PaymentFrequency;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ScheduleSpent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spent_id", nullable = false)
    @JsonIgnore
    private Spent spent;

    // @NotNull(message = "Start date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    private Integer numberOfStallments;

    @Enumerated(EnumType.STRING)
    private PaymentFrequency frequency;
    
    // @NotNull(message = "Day of month is required")
    private Integer dayOfMonth;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Spent getSpent() {
        return spent;
    }
    public void setSpent(Spent spent) {
        this.spent = spent;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public Integer getNumberOfStallments() {
        return numberOfStallments;
    }
    public void setNumberOfStallments(Integer numberOfStallments) {
        this.numberOfStallments = numberOfStallments;
    }
    public PaymentFrequency getFrequency() {
        return frequency;
    }
    public void setFrequency(PaymentFrequency frequency) {
        this.frequency = frequency;
    }
    public Integer getDayOfMonth() {
        return dayOfMonth;
    }
    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}
