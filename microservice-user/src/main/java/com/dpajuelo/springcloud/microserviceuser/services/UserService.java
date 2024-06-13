package com.dpajuelo.springcloud.microserviceuser.services;

import com.dpajuelo.springcloud.microserviceuser.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    User save(User user);
    void deleteById(Long id);

    List<User> findAllById(List<Long> ids);

    Optional<User> findByEmail(String email);
}
