package com.cashlog.cashlog.servicies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashlog.cashlog.dto.MonetaryFundRequestDTO;
import com.cashlog.cashlog.dto.MonetaryFundUpdateDTO;
import com.cashlog.cashlog.entities.MonetaryFund;
import com.cashlog.cashlog.entities.User;
import com.cashlog.cashlog.repositories.IMonetaryFundRepository;
import com.cashlog.cashlog.repositories.IUserRepository;

@Service
public class MonetaryFundServiceImpl implements IMonetaryFundService{

    @Autowired
    private IMonetaryFundRepository monetaryFundRepository;

    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MonetaryFundRequestDTO> findAll() {
        List<MonetaryFund> monetaryFunds = new ArrayList<>();
        this.monetaryFundRepository.findAll().forEach(monetaryFunds::add);
        return listMonetaryToListDTO(monetaryFunds);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MonetaryFundRequestDTO> findById(Long id) {
        Optional<MonetaryFund> monetaryOptional = this.monetaryFundRepository.findById(id);
        if(monetaryOptional.isPresent()){
            return Optional.of(monetaryToDTO(monetaryOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public MonetaryFund save(MonetaryFundRequestDTO monetaryFundRequestDTO) {
        MonetaryFund monetaryFund = dtoToMonetaryFund(monetaryFundRequestDTO);
        User user = getUserForDTO(monetaryFundRequestDTO);
        user.addFunds(monetaryFund);
        return this.monetaryFundRepository.save(monetaryFund);
    }

    @Override
    @Transactional
    public Optional<MonetaryFundRequestDTO> update(Long id, MonetaryFundUpdateDTO monetaryFund) {
        Optional<MonetaryFund> monetaryOptional = this.monetaryFundRepository.findById(id);
        if(monetaryOptional.isPresent()){
            MonetaryFund monetaryFundBD = monetaryOptional.get();
            monetaryFundBD.setName(monetaryFund.getName());
            monetaryFundBD.setDescription(monetaryFund.getDescription());
            monetaryFundBD.setNumber(monetaryFund.getNumber());
            monetaryFundBD.setFundsCategory(monetaryFund.getFundsCategory());
            return Optional.of(monetaryToDTO(this.monetaryFundRepository.save(monetaryFundBD)));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.monetaryFundRepository.deleteById(id);
    }

    private MonetaryFund dtoToMonetaryFund(MonetaryFundRequestDTO monetaryFundRequestDTO){
        MonetaryFund monetaryFund = new MonetaryFund();
        monetaryFund.setName(monetaryFundRequestDTO.getName());
        monetaryFund.setNumber(monetaryFundRequestDTO.getNumber());
        monetaryFund.setDescription(monetaryFundRequestDTO.getDescription());
        monetaryFund.setFundsCategory(monetaryFundRequestDTO.getFundsCategory());
        return monetaryFund;
    }

    private MonetaryFundRequestDTO monetaryToDTO(MonetaryFund monetaryFund){
        MonetaryFundRequestDTO monetaryFundRequestDTO = new MonetaryFundRequestDTO();
        monetaryFundRequestDTO.setId(monetaryFund.getId());
        monetaryFundRequestDTO.setName(monetaryFund.getName());
        monetaryFundRequestDTO.setDescription(monetaryFund.getDescription());
        monetaryFundRequestDTO.setNumber(monetaryFund.getNumber());
        monetaryFundRequestDTO.setFundsCategory(monetaryFund.getFundsCategory());
        monetaryFundRequestDTO.setUserId(monetaryFund.getUser().getId());
        return monetaryFundRequestDTO;
    }

    private List<MonetaryFundRequestDTO> listMonetaryToListDTO(List<MonetaryFund> funds){
        return funds.stream()
            .map(this::monetaryToDTO)
            .collect(Collectors.toList());
    }

    private User getUserForDTO(MonetaryFundRequestDTO monetaryFundRequestDTO){
        return this.userRepository.findById(monetaryFundRequestDTO.getUserId()).orElseThrow(
            () -> new RuntimeException("User not found with id: " + monetaryFundRequestDTO.getUserId())
        );
    }
}
