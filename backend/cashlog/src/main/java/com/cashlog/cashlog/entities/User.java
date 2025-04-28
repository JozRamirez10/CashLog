package com.cashlog.cashlog.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Lastname is required")
    private String lastname;

    @NotEmpty(message = "Email is required")
    @Email(message = "It doesn't have an email format")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "The password must be 4 characters long")
    private String password;

    @OneToMany(
        fetch = FetchType.LAZY,
        mappedBy = "user", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )
    @JsonIgnore
    private List<MonetaryFund> funds = new ArrayList<>();

    public void addFunds(MonetaryFund fund){
        this.funds.add(fund);
        fund.setUser(this);
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
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<MonetaryFund> getFunds() {
        return funds;
    }
    public void setFunds(List<MonetaryFund> funds) {
        this.funds = funds;
    }
   
}
