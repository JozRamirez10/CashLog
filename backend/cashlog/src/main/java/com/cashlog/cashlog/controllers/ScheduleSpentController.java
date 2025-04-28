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

import com.cashlog.cashlog.entities.ScheduleSpent;
import com.cashlog.cashlog.servicies.IScheduleSpentService;
import com.cashlog.cashlog.utils.Utils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleSpentController {
    
    @Autowired
    private IScheduleSpentService scheduleSpentService;

    @GetMapping
    public List<ScheduleSpent> list(){
        return this.scheduleSpentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> byId(@PathVariable Long id){
        Optional<ScheduleSpent> scheduleOptional = this.scheduleSpentService.findById(id);
        if(scheduleOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(scheduleOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("Error","Schedule Spent not found"));
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody ScheduleSpent scheduleSpent, BindingResult result){
        if(result.hasErrors()){
            return Utils.validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.scheduleSpentService.save(scheduleSpent));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody ScheduleSpent scheduleSpent, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return Utils.validation(result);
        }
        Optional<ScheduleSpent> scheduleOptional = this.scheduleSpentService.update(id, scheduleSpent);
        if(scheduleOptional.isPresent()){
            return ResponseEntity.ok(scheduleOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<ScheduleSpent> scheduleOptional = this.scheduleSpentService.findById(id);
        if(scheduleOptional.isPresent()){
            this.scheduleSpentService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
