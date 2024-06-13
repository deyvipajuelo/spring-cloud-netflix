package com.dpajuelo.springcloud.microserviceuser.repositories;

import com.dpajuelo.springcloud.microserviceuser.models.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
