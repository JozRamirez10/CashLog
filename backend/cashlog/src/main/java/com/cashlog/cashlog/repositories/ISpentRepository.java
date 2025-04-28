package com.cashlog.cashlog.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cashlog.cashlog.entities.Spent;

public interface ISpentRepository extends CrudRepository<Spent, Long>{

}
