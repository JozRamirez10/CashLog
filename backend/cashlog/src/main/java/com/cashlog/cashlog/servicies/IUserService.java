package com.cashlog.cashlog.servicies;

import java.util.List;
import java.util.Optional;

import com.cashlog.cashlog.dto.UserUpdateDTO;
import com.cashlog.cashlog.entities.User;

public interface IUserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    Optional<User> update(Long id, UserUpdateDTO user);
    void deleteById(Long id);

    boolean existsByEmail(String email);
    boolean existsByEmailAndId(String email, Long id);
}
