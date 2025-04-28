package com.cashlog.cashlog.entities;

import java.util.ArrayList;
import java.util.List;

import com.cashlog.cashlog.enums.FundsCategory;
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
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class MonetaryFund {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    // Only debit or credit
    private String number;

    private String description;

    @Enumerated(EnumType.STRING)
    private FundsCategory fundsCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "monetaryFund", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )
    @JsonIgnore
    private List<Spent> spents = new ArrayList<>();

    public void addSpent(Spent spent){
        this.spents.add(spent);
        spent.setMonetaryFund(this);
    }

    public void removeSpent(Spent spent){
        this.spents.remove(spent);
        spent.setMonetaryFund(null);
    }

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

    public String getNumber() {
        return number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public FundsCategory getFundsCategory() {
        return fundsCategory;
    }

    public void setFundsCategory(FundsCategory fundsCategory) {
        this.fundsCategory = fundsCategory;
    }

    public List<Spent> getSpents() {
        return spents;
    }

    public void setSpents(List<Spent> spents) {
        this.spents = spents;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
