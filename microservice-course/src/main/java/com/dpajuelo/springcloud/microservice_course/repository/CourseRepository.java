package com.dpajuelo.springcloud.microservice_course.repository;

import com.dpajuelo.springcloud.microservice_course.model.entity.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Query("delete from CursoUsuario cu where cu.userId = :userId")
    void deleteUserFromAllCourses(Long userId);
}
