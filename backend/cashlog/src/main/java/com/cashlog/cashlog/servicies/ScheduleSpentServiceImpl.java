package com.cashlog.cashlog.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashlog.cashlog.entities.ScheduleSpent;
import com.cashlog.cashlog.repositories.IScheduleSpentRepository;

@Service
public class ScheduleSpentServiceImpl implements IScheduleSpentService{

    @Autowired
    private IScheduleSpentRepository scheduleSpentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleSpent> findAll() {
        return (List<ScheduleSpent>) this.scheduleSpentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ScheduleSpent> findById(Long id) {
        Optional<ScheduleSpent> scheduleSpentOptional = this.scheduleSpentRepository.findById(id);
        if(scheduleSpentOptional.isPresent()){
            return Optional.of(scheduleSpentOptional.get());
        }
        return Optional.empty();
    }

    @Override
    public ScheduleSpent save(ScheduleSpent scheduleSpent) {
        return this.scheduleSpentRepository.save(scheduleSpent);
    }

    @Override
    public Optional<ScheduleSpent> update(Long id, ScheduleSpent scheduleSpent) {
        Optional<ScheduleSpent> scheduleSpentOptional = this.scheduleSpentRepository.findById(id);
        if(scheduleSpentOptional.isPresent()){
            ScheduleSpent scheduleSpentBD = scheduleSpentOptional.get();
            scheduleSpentBD.setSpent(scheduleSpent.getSpent());
            scheduleSpentBD.setStartDate(scheduleSpent.getStartDate());
            scheduleSpentBD.setNumberOfStallments(scheduleSpent.getNumberOfStallments());
            scheduleSpentBD.setFrequency(scheduleSpent.getFrequency());
            scheduleSpentBD.setDayOfMonth(scheduleSpent.getDayOfMonth());
            return Optional.of(this.scheduleSpentRepository.save(scheduleSpentBD));
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {
        this.scheduleSpentRepository.deleteById(id);
    }




}
