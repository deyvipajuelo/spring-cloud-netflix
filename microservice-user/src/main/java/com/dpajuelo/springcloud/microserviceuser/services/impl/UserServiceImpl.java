package com.dpajuelo.springcloud.microserviceuser.services.impl;

import com.dpajuelo.springcloud.microserviceuser.clients.CourseClientRest;
import com.dpajuelo.springcloud.microserviceuser.models.entity.User;
import com.dpajuelo.springcloud.microserviceuser.repositories.UserRepository;
import com.dpajuelo.springcloud.microserviceuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;
    private CourseClientRest courseClient;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CourseClientRest courseClient) {
        this.userRepository = userRepository;
        this.courseClient = courseClient;
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
        courseClient.eliminarUsuarioDeTodosLosCursos(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllById(List<Long> ids) {
        return (List<User>) userRepository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
