package com.cashlog.cashlog.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cashlog.cashlog.entities.MonetaryFund;

public interface IMonetaryFundRepository extends CrudRepository<MonetaryFund, Long>{

}
