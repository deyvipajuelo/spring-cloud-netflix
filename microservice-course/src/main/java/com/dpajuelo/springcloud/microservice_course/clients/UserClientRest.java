package com.dpajuelo.springcloud.microservice_course.clients;

import com.dpajuelo.springcloud.microservice_course.model.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "microservice-user", url = "${microservice.user.url}")
@FeignClient(name = "microservice-user", url = "localhost:8080/user")
public interface UserClientRest {

    @GetMapping("/{id}")
    UserDto getUserById(@PathVariable Long id);

    @PostMapping
    UserDto saveUser(@RequestBody UserDto userDto);

    @GetMapping("/usuarios-por-id")
    List<UserDto> getUsersById(@RequestParam List<Long> ids);

}
