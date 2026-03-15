package com.example.confeitariaMacedoFarias.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.confeitariaMacedoFarias.dto.UserDto;
import com.example.confeitariaMacedoFarias.dto.UserInsertDto;
import com.example.confeitariaMacedoFarias.entities.User;
import com.example.confeitariaMacedoFarias.exceptions.DatabaseException;
import com.example.confeitariaMacedoFarias.exceptions.ResourceNotFoundException;
import com.example.confeitariaMacedoFarias.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    @Transactional(readOnly = true)
    public UserInsertDto findById(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id " + id + " não encontrado"));
        return new UserInsertDto(user);
    }

    @Transactional(readOnly = true)
    public Page<UserInsertDto> findAll(Pageable pageable) {
        Page<User> result = repository.findAll(pageable);
        return result.map(UserInsertDto::new);
    }

    @Transactional
    public UserDto insert(UserInsertDto dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new UserDto(entity.getId(), entity.getEmail());
    }

    @Transactional
    public UserInsertDto update(Long id, UserInsertDto dto) {
        User entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com id " + id + " não encontrado"));
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new UserInsertDto(entity);
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível deletar usuário com referências existentes");
        }
    }

    private void copyDtoToEntity(UserInsertDto dto, User entity) {
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
    }
}
