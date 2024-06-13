package com.dpajuelo.springcloud.microservice_course.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "curso_usuario")
@Setter
@Getter
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: habra problemas con unique
    @Column(name = "user_id", unique = true)
    private Long userId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CursoUsuario)) {
            return false;
        }
        CursoUsuario cursoUsuario = (CursoUsuario) obj;
        return this.userId != null && this.userId.equals(cursoUsuario.getUserId());
    }
}
