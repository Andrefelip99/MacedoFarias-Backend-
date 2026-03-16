package com.example.confeitariaMacedoFarias.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.UserDto;
import com.example.confeitariaMacedoFarias.dto.UserInsertDto;
import com.example.confeitariaMacedoFarias.entities.Role;
import com.example.confeitariaMacedoFarias.entities.User;
import com.example.confeitariaMacedoFarias.exceptions.DatabaseException;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario com id " + id + " nao encontrado"));
        return new UserDto(user.getId(), user.getEmail());
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> result = repository.findAll(pageable);
        return result.map(user -> new UserDto(user.getId(), user.getEmail()));
    }

    @Transactional
    public UserDto insert(UserInsertDto dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new UserDto(entity.getId(), entity.getEmail());
    }

    @Transactional
    public UserDto update(Long id, UserInsertDto dto) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario com id " + id + " nao encontrado"));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new UserDto(entity.getId(), entity.getEmail());
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Nao e possivel deletar usuario com referencias existentes");
        }
    }

    private void copyDtoToEntity(UserInsertDto dto, User entity) {
        entity.setEmail(dto.getEmail());
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        if (entity.getRole() == null) {
            entity.setRole(Role.USER);
        }
    }
}
