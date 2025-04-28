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

import com.cashlog.cashlog.dto.SpentRequestDTO;
import com.cashlog.cashlog.enums.ExpenseType;
import com.cashlog.cashlog.exceptions.BadRequestScheduleSpentException;
import com.cashlog.cashlog.servicies.ISpentService;
import com.cashlog.cashlog.utils.Utils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/spents")
public class SpentController {
    
    @Autowired
    private ISpentService spentService;

    @GetMapping
    public List<SpentRequestDTO> list(){
        return this.spentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id){
        Optional<SpentRequestDTO> spentOptional = this.spentService.findById(id);
        if(spentOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(spentOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "Spent not found"));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody SpentRequestDTO spent, BindingResult result){
        if(result.hasErrors()){
            return Utils.validation(result);
        }
        validationScheduleSpent(spent);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.spentService.save(spent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody SpentRequestDTO spent, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return Utils.validation(result);
        }
        validationScheduleSpent(spent);
        Optional<SpentRequestDTO> spentOptional = this.spentService.update(id, spent);
        if(spentOptional.isPresent()){
            return ResponseEntity.ok(spentOptional.get());
        }   
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<SpentRequestDTO> spentOptional = this.spentService.findById(id);
        if(spentOptional.isPresent()){
            this.spentService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private void validationScheduleSpent(SpentRequestDTO spent){
        if(spent.getExpenseType() == ExpenseType.INSTALLMENTS || spent.getExpenseType() == ExpenseType.RECURRING){
            if(spent.getScheduleSpent() == null){
                throw new BadRequestScheduleSpentException("Schedule data is required for this expense type");
            }
            if(spent.getScheduleSpent().getFrequency() == null){
                throw new BadRequestScheduleSpentException("Frequency is required for this expense type");
            }
            Integer dayOfMonth = spent.getScheduleSpent().getDayOfMonth();
            if(dayOfMonth == null || dayOfMonth < 1 || dayOfMonth > 31){
                throw new BadRequestScheduleSpentException("Day of month not cumply with the format");
            }
        }

        if(spent.getExpenseType() == ExpenseType.INSTALLMENTS){
            if(spent.getScheduleSpent().getStartDate() ==  null){
                throw new BadRequestScheduleSpentException("Start date is required");
            }
            if(spent.getScheduleSpent().getNumberOfStallments() == null || spent.getScheduleSpent().getNumberOfStallments() <= 0 ){
                throw new BadRequestScheduleSpentException("Number of stallments is required");
            }
        }
    }
}
