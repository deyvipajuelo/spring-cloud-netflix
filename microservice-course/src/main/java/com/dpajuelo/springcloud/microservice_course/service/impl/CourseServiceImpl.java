package com.dpajuelo.springcloud.microservice_course.service.impl;

import com.dpajuelo.springcloud.microservice_course.clients.UserClientRest;
import com.dpajuelo.springcloud.microservice_course.model.dto.UserDto;
import com.dpajuelo.springcloud.microservice_course.model.entity.Course;
import com.dpajuelo.springcloud.microservice_course.model.entity.CursoUsuario;
import com.dpajuelo.springcloud.microservice_course.repository.CourseRepository;
import com.dpajuelo.springcloud.microservice_course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserClientRest clientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findByIdWithUsers(Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            Course curso = course.get();
            if (!curso.getCursosUsuario().isEmpty()) {
                List<Long> ids = course.get().getCursosUsuario()
                        .stream().map(user -> user.getUserId()).collect(Collectors.toList());

                List<UserDto> users = clientRest.getUsersById(ids);
                course.get().setUsers(users);
            }
            return course;
        }

        return Optional.empty();
    }

    /**
     * Asignar usuario ya creado a un curso
     * @param userDto
     * @param cursoId
     * @return
     */
    @Override
    @Transactional
    public Optional<UserDto> asignarUsuario(UserDto userDto, Long cursoId) {
        Optional<Course> curso = findById(cursoId);

        if (curso.isPresent()) {
            UserDto userMsvc = clientRest.getUserById(userDto.getId());

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUserId(userMsvc.getId());

            Course cursoToSave = curso.get();
            cursoToSave.addCursoUsuario(cursoUsuario);
            courseRepository.save(cursoToSave);
            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }

    /**
     * Crear un nuevo usuario y asignarlo a un curso
     * @param userDto
     * @param cursoId
     * @return
     */
    @Override
    @Transactional
    public Optional<UserDto> crearAsignarUsuario(UserDto userDto, Long cursoId) {
        Optional<Course> curso = findById(cursoId);

        if (curso.isPresent()) {
            UserDto userMsvc = clientRest.saveUser(userDto);

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUserId(userMsvc.getId());

            Course cursoToSave = curso.get();
            cursoToSave.addCursoUsuario(cursoUsuario);
            courseRepository.save(cursoToSave);
            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }

    /**
     * Eliminar un usuario de un curso
     * @param userDto
     * @param cursoId
     * @return
     */
    @Override
    @Transactional
    public Optional<UserDto> eliminarUsuarioDeUnCurso(UserDto userDto, Long cursoId) {
        Optional<Course> curso = findById(cursoId);

        if (curso.isPresent()) {
            UserDto userMsvc = clientRest.getUserById(userDto.getId());

            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUserId(userDto.getId());

            Course cursoToSave = curso.get();
            cursoToSave.removeCursoUsuario(cursoUsuario);

            courseRepository.save(cursoToSave);
            return Optional.of(userDto);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public void eliminarUsuarioDeTodosLosCursos(Long userId) {
        courseRepository.deleteUserFromAllCourses(userId);
    }
}
