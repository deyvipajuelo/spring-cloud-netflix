package com.dpajuelo.springcloud.microservice_course.model.entity;

import com.dpajuelo.springcloud.microservice_course.model.dto.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursosUsuario;

    @Transient
    private List<UserDto> users;

    public Course() {
        this.cursosUsuario = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public void addCursoUsuario(CursoUsuario cursoUsuario) {
        this.cursosUsuario.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario) {
        this.cursosUsuario.remove(cursoUsuario);
    }

}
