package com.cashlog.cashlog.repositories;

import org.springframework.data.repository.CrudRepository;

import com.cashlog.cashlog.entities.User;

public interface IUserRepository extends CrudRepository<User, Long>{

    boolean existsByEmail(String email);

    boolean existsByEmailAndId(String email, Long id);

}
