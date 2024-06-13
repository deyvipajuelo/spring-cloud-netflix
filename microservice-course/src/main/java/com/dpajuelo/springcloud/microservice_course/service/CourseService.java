package com.dpajuelo.springcloud.microservice_course.service;

import com.dpajuelo.springcloud.microservice_course.model.dto.UserDto;
import com.dpajuelo.springcloud.microservice_course.model.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findAll();
    Optional<Course> findById(Long id);
    Course save(Course course);
    void deleteById(Long id);

    Optional<Course> findByIdWithUsers(Long id);
    Optional<UserDto> asignarUsuario(UserDto useDto, Long cursoId);
    Optional<UserDto> crearAsignarUsuario(UserDto useDto, Long cursoId);
    Optional<UserDto> eliminarUsuarioDeUnCurso(UserDto useDto, Long cursoId);

    void eliminarUsuarioDeTodosLosCursos(Long userId);
}
