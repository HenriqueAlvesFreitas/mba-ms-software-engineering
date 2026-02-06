package br.com.software_engineering.application.controller;


import br.com.software_engineering.application.domain.User;
import br.com.software_engineering.application.dtos.request.UserDTO;
import br.com.software_engineering.application.dtos.response.PageResponse;
import br.com.software_engineering.application.port.UserPort;
import br.com.software_engineering.application.service.user.UserService;
import br.com.software_engineering.infra.web.RestResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserPort service;

    @PostMapping("/save")
    public ResponseEntity<RestResponse<User>> save(@RequestBody @Valid UserDTO dto){
        return service.save(dto);
    }

    @GetMapping("/all")
    public ResponseEntity<RestResponse<PageResponse<User>>> get(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") int page
    ){
        return service.getAll(name, email, phone, page);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RestResponse<User>> update(@RequestBody @Valid UserDTO dto, @PathVariable UUID id){
        return service.updateUser(dto, id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RestResponse<Object>> delete(@PathVariable UUID id){
        return service.deleteUser(id);
    }



}
