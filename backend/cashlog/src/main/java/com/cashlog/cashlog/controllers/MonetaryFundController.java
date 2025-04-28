package com.cashlog.cashlog.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cashlog.cashlog.dto.MonetaryFundRequestDTO;
import com.cashlog.cashlog.dto.MonetaryFundUpdateDTO;
import com.cashlog.cashlog.servicies.IMonetaryFundService;
import com.cashlog.cashlog.utils.Utils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/funds")
public class MonetaryFundController {

    @Autowired
    private IMonetaryFundService monetaryFundService;

    @GetMapping
    public List<MonetaryFundRequestDTO> list(){
        return this.monetaryFundService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id){
        Optional<MonetaryFundRequestDTO> monetaryOptional = this.monetaryFundService.findById(id);
        if(monetaryOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(monetaryOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Error", "Monetary Fund not found"));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MonetaryFundRequestDTO monetaryFundRequestDTO, BindingResult result){
        if(result.hasErrors()){
            return Utils.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.monetaryFundService.save(monetaryFundRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody MonetaryFundUpdateDTO monetaryFund, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return Utils.validation(result);
        }
        Optional<MonetaryFundRequestDTO> monetaryOptional = this.monetaryFundService.update(id, monetaryFund);
        if(monetaryOptional.isPresent()){
            return ResponseEntity.ok(monetaryOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<MonetaryFundRequestDTO> monetaryOptional = this.monetaryFundService.findById(id);
        if(monetaryOptional.isPresent()){
            this.monetaryFundService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
