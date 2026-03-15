package com.example.confeitariaMacedoFarias.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.confeitariaMacedoFarias.dto.UserInsertDto;
import com.example.confeitariaMacedoFarias.dto.UserDto;
import com.example.confeitariaMacedoFarias.services.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

  
    private final UserService service;

    @GetMapping(value = "/{id}")
    public UserInsertDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public Page<UserInsertDto> findAll(Pageable pageable) {
        return service.findAll(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto insert(@Valid @RequestBody UserInsertDto dto) {
        return service.insert(dto);
    }

    @PutMapping(value = "/{id}")
    public UserInsertDto update(@PathVariable Long id, @Valid @RequestBody UserInsertDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

}