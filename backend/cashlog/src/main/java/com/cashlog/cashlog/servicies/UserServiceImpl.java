package com.cashlog.cashlog.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cashlog.cashlog.dto.UserUpdateDTO;
import com.cashlog.cashlog.entities.User;
import com.cashlog.cashlog.repositories.IUserRepository;

@Service
public class UserServiceImpl implements IUserService{
    
    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if(userOptional.isPresent()){
            return Optional.of(userOptional.get());
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public User save(User user) {
        // Password encode
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public Optional<User> update(Long id, UserUpdateDTO user) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if(userOptional.isPresent()){
            User userBD = userOptional.get();
            userBD.setName(user.getName());
            userBD.setLastname(user.getLastname());
            return Optional.of(this.userRepository.save(userBD));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmailAndId(String email, Long id) {
        return this.userRepository.existsByEmailAndId(email, id);
    }

}
