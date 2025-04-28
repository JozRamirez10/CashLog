package com.cashlog.cashlog.servicies;

import java.util.List;
import java.util.Optional;

import com.cashlog.cashlog.dto.SpentRequestDTO;
import com.cashlog.cashlog.entities.Spent;

public interface ISpentService {
    List<SpentRequestDTO> findAll();
    Optional<SpentRequestDTO> findById(Long id);
    Spent save(SpentRequestDTO spentRequestDTO);
    Optional<SpentRequestDTO> update(Long id, SpentRequestDTO spent);
    void deleteById(Long id);
}
