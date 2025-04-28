package com.cashlog.cashlog.servicies;

import java.util.List;
import java.util.Optional;

import com.cashlog.cashlog.dto.MonetaryFundRequestDTO;
import com.cashlog.cashlog.dto.MonetaryFundUpdateDTO;
import com.cashlog.cashlog.entities.MonetaryFund;

public interface IMonetaryFundService {
    List<MonetaryFundRequestDTO> findAll();
    Optional<MonetaryFundRequestDTO> findById(Long id);
    MonetaryFund save(MonetaryFundRequestDTO monetaryFundRequestDTO);
    Optional<MonetaryFundRequestDTO> update(Long id, MonetaryFundUpdateDTO monetaryFund);
    void deleteById(Long id);
}
