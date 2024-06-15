package com.dpajuelo.springcloud.microserviceuser.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name = "microservice-course", url = "${microservice.cuorse.url}")
@FeignClient(name = "microservice-course")
public interface CourseClientRest {

    @DeleteMapping("/eliminar-usuario-cursos/{userId}")
    void eliminarUsuarioDeTodosLosCursos(@PathVariable Long userId);

}
