package br.com.software_engineering.application.controller;


import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.dtos.request.UserDTO;
import br.com.software_engineering.application.service.UserService;
import br.com.software_engineering.infra.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping("/save")
    public ResponseEntity<RestResponse<User>> save(@RequestBody @Valid UserDTO dto){
        return service.save(dto);
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<User>>> get(){
        return service.getAll();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RestResponse<User>> update(@RequestBody @Valid UserDTO dto, @PathVariable UUID id){
        return service.updateUser(dto, id);
    }

}
