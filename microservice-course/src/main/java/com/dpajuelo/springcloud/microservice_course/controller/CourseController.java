package com.dpajuelo.springcloud.microservice_course.controller;

import com.dpajuelo.springcloud.microservice_course.model.dto.UserDto;
import com.dpajuelo.springcloud.microservice_course.model.entity.Course;
import com.dpajuelo.springcloud.microservice_course.service.CourseService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id) {
//        Optional<Course> course = courseService.findById(id);
        Optional<Course> course = courseService.findByIdWithUsers(id);
        if (course.isPresent()) {
            return ResponseEntity.ok(course);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> saveCourse(@Valid @RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@Valid @RequestBody Course course, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Course> courseDB = courseService.findById(id);
        if (courseDB.isPresent()) {
            Course courseSave = course;
            courseSave.setId(id);
            return ResponseEntity.ok(courseService.save(courseSave));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        Optional<Course> courseBD = courseService.findById(id);
        if (courseBD.isPresent()) {
            courseService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuarioACurso(@RequestBody UserDto userDto, @PathVariable Long cursoId) {
        Optional<UserDto> user;
        try {
            user = courseService.asignarUsuario(userDto,cursoId);
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("Mensaje", "Error: " + ex.getMessage()));
        }

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-asignar-usuario/{cursoId}")
    public ResponseEntity<?> crearAsignarUsuarioACurso(@RequestBody UserDto userDto, @PathVariable Long cursoId) {
        Optional<UserDto> user;
        try {
            user = courseService.crearAsignarUsuario(userDto,cursoId);
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("Mensaje", "Error: " + ex.getMessage()));
        }

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuarioDeUnCurso(@RequestBody UserDto userDto, @PathVariable Long cursoId) {
        Optional<UserDto> user;
        try {
            user = courseService.eliminarUsuarioDeUnCurso(userDto,cursoId);
        } catch (FeignException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("Mensaje", "Error: " + ex.getMessage()));
        }

        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario-cursos/{userId}")
    public ResponseEntity<?> eliminarUsuarioDeTodosLosCursos(@PathVariable Long userId) {
        courseService.eliminarUsuarioDeTodosLosCursos(userId);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach((err) -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
