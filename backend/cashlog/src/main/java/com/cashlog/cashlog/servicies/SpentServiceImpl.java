package com.cashlog.cashlog.servicies;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashlog.cashlog.dto.SpentRequestDTO;
import com.cashlog.cashlog.entities.MonetaryFund;
import com.cashlog.cashlog.entities.ScheduleSpent;
import com.cashlog.cashlog.entities.Spent;
import com.cashlog.cashlog.enums.ExpenseType;
import com.cashlog.cashlog.repositories.IMonetaryFundRepository;
import com.cashlog.cashlog.repositories.IScheduleSpentRepository;
import com.cashlog.cashlog.repositories.ISpentRepository;

@Service
public class SpentServiceImpl implements ISpentService{

    @Autowired
    private ISpentRepository spentRepository;

    @Autowired
    private IMonetaryFundRepository monetaryFundRepository;

    @Autowired
    private IScheduleSpentRepository scheduleSpentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SpentRequestDTO> findAll() {
        List<Spent> spents = new ArrayList<>();
        this.spentRepository.findAll().forEach(spents::add);
        return listSpentToListDTO(spents);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SpentRequestDTO> findById(Long id) {
        Optional<Spent> spentOptional = this.spentRepository.findById(id);
        if(spentOptional.isPresent()){
            return Optional.of(spentToDTO(spentOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Spent save(SpentRequestDTO spentRequestDTO) {
        Spent spent = dtoToSpent(spentRequestDTO);

        MonetaryFund monetaryFund = getMonetaryFundForDTO(spentRequestDTO);
        monetaryFund.addSpent(spent);

        if(spent.getExpenseType() == ExpenseType.INSTALLMENTS ||
            spent.getExpenseType() == ExpenseType.RECURRING){
            
            ScheduleSpent scheduleSpent = spentRequestDTO.getScheduleSpent();
            if(scheduleSpent == null){
                throw new IllegalArgumentException("Schedule data is required for this expense type");
            }

            scheduleSpent.setSpent(spent);
            spent.setScheduleSpent(scheduleSpent);
        }
        
        return this.spentRepository.save(spent);
    }

    @Override
    @Transactional
    public Optional<SpentRequestDTO> update(Long id, SpentRequestDTO spent) {
        Optional<Spent> spentOptional = this.spentRepository.findById(id);
        if(spentOptional.isPresent()){
            Spent spentBD = spentOptional.get();
            spentBD.setName(spent.getName());
            spentBD.setDescription(spent.getDescription());
            spentBD.setAmount(spent.getAmount());
            spentBD.setExpenseType(spent.getExpenseType());

            MonetaryFund monetaryFund = getMonetaryFundForDTO(spent);
            if(!monetaryFund.getId().equals(spentBD.getMonetaryFund().getId())){
                MonetaryFund monetaryFundBD = spentBD.getMonetaryFund();
                monetaryFundBD.removeSpent(spentBD);
                monetaryFund.addSpent(spentBD);
            }

            if(spent.getExpenseType() == ExpenseType.INSTALLMENTS ||
                spent.getExpenseType() == ExpenseType.RECURRING){
                
                ScheduleSpent scheduleSpent = spent.getScheduleSpent();
                if(scheduleSpent == null){
                    throw new IllegalArgumentException("Schedule data is required for this expense type");
                }

                ScheduleSpent scheduleSpentBD = spentBD.getScheduleSpent();
                if(scheduleSpentBD == null){
                    scheduleSpentBD = new ScheduleSpent();
                    scheduleSpentBD.setSpent(spentBD);
                }

                scheduleSpentBD.setStartDate(scheduleSpent.getStartDate());
                scheduleSpentBD.setNumberOfStallments(scheduleSpent.getNumberOfStallments());
                scheduleSpentBD.setFrequency(scheduleSpent.getFrequency());
                scheduleSpentBD.setDayOfMonth(scheduleSpent.getDayOfMonth());

                scheduleSpentBD.setSpent(spentBD);
                spentBD.setScheduleSpent(scheduleSpentBD);
            }else{
                if(spentBD.getScheduleSpent() != null){
                    ScheduleSpent scheduleSpent = spentBD.getScheduleSpent();
                    scheduleSpentRepository.deleteById(scheduleSpent.getId());
                    spentBD.setScheduleSpent(null);
                }
            }

            return Optional.of(spentToDTO(this.spentRepository.save(spentBD)));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.spentRepository.deleteById(id);
    }

    private Spent dtoToSpent(SpentRequestDTO spentRequestDTO){
        Spent spent = new Spent();
        spent.setName(spentRequestDTO.getName());
        spent.setDescription(spentRequestDTO.getDescription());
        spent.setAmount(spentRequestDTO.getAmount());
        spent.setExpenseType(spentRequestDTO.getExpenseType());
        return spent;
    }

    private SpentRequestDTO spentToDTO(Spent spent){
        SpentRequestDTO spentRequestDTO = new SpentRequestDTO();
        spentRequestDTO.setId(spent.getId());
        spentRequestDTO.setName(spent.getName());
        spentRequestDTO.setDescription(spent.getDescription());
        spentRequestDTO.setAmount(spent.getAmount());
        spentRequestDTO.setExpenseType(spent.getExpenseType());
        spentRequestDTO.setMonetaryFundId(spent.getMonetaryFund().getId());

        if(spent.getExpenseType() == ExpenseType.INSTALLMENTS || spent.getExpenseType() == ExpenseType.RECURRING){
            spentRequestDTO.setScheduleSpent(spent.getScheduleSpent());
        }

        return spentRequestDTO;
    }

    private List<SpentRequestDTO> listSpentToListDTO(List<Spent> spents){
        return spents.stream()
            .map(this::spentToDTO)
            .collect(Collectors.toList());
    }

    private MonetaryFund getMonetaryFundForDTO(SpentRequestDTO spentRequestDTO){
        return this.monetaryFundRepository.findById(spentRequestDTO.getMonetaryFundId()).orElseThrow(
            () -> new RuntimeException("Monetary fund not found with id: " + spentRequestDTO.getMonetaryFundId())
        );
    }
}
