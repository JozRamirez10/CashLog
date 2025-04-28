package com.cashlog.cashlog.servicies;

import java.util.List;
import java.util.Optional;

import com.cashlog.cashlog.entities.ScheduleSpent;

public interface IScheduleSpentService {
    List<ScheduleSpent> findAll();
    Optional<ScheduleSpent> findById(Long id);
    ScheduleSpent save(ScheduleSpent scheduleSpent);
    Optional<ScheduleSpent> update(Long id, ScheduleSpent scheduleSpent);
    void deleteById(Long id);
}
